package handlers

import (
	"encoding/json"
	"net/http"
	"strconv"

	"github.com/gorilla/mux"

	"banking/models"
	"banking/repositories"
)

// ErrorResponse represents a JSON error response
type ErrorResponse struct {
	Error string `json:"error"`
}

// CreateUserRequest represents the request to create a user
type CreateUserRequest struct {
	Username string `json:"username"`
	Email    string `json:"email"`
}

// CreateAccountRequest represents the request to create an account
type CreateAccountRequest struct {
	UserID         int64   `json:"user_id"`
	AccountType    string  `json:"account_type"`
	InitialBalance float64 `json:"initial_balance"`
}

// TransactionRequest represents a deposit or withdrawal request
type TransactionRequest struct {
	Amount float64 `json:"amount"`
}

// CreateUserHandler handles user creation
func CreateUserHandler(w http.ResponseWriter, r *http.Request) {
	var req CreateUserRequest
	if err := json.NewDecoder(r.Body).Decode(&req); err != nil {
		sendError(w, "Invalid request body", http.StatusBadRequest)
		return
	}

	if req.Username == "" || req.Email == "" {
		sendError(w, "Username and email are required", http.StatusBadRequest)
		return
	}

	userID, err := repositories.CreateUser(req.Username, req.Email)
	if err != nil {
		sendError(w, "Failed to create user: "+err.Error(), http.StatusInternalServerError)
		return
	}

	user, err := repositories.GetUserByID(userID)
	if err != nil {
		sendError(w, "User created but unable to retrieve: "+err.Error(), http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(http.StatusCreated)
	json.NewEncoder(w).Encode(user)
}

// CreateAccountHandler handles account creation
func CreateAccountHandler(w http.ResponseWriter, r *http.Request) {
	var req CreateAccountRequest
	if err := json.NewDecoder(r.Body).Decode(&req); err != nil {
		sendError(w, "Invalid request body", http.StatusBadRequest)
		return
	}

	// Validate account type
	if req.AccountType != "Savings" && req.AccountType != "Current" {
		sendError(w, "Account type must be either 'Savings' or 'Current'", http.StatusBadRequest)
		return
	}

	// Check if the user exists
	_, err := repositories.GetUserByID(req.UserID)
	if err != nil {
		sendError(w, "User not found", http.StatusNotFound)
		return
	}

	// Create the account
	accountID, err := repositories.CreateAccount(req.UserID, req.AccountType, req.InitialBalance)
	if err != nil {
		sendError(w, "Failed to create account: "+err.Error(), http.StatusInternalServerError)
		return
	}

	// Retrieve the created account
	account, err := repositories.GetAccountByID(accountID)
	if err != nil {
		sendError(w, "Account created but unable to retrieve: "+err.Error(), http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(http.StatusCreated)
	json.NewEncoder(w).Encode(account)
}

// GetAccountsHandler retrieves all accounts for a user
func GetAccountsHandler(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	userID, err := strconv.ParseInt(vars["user_id"], 10, 64)
	if err != nil {
		sendError(w, "Invalid user ID", http.StatusBadRequest)
		return
	}

	// Check if the user exists
	_, err = repositories.GetUserByID(userID)
	if err != nil {
		sendError(w, "User not found", http.StatusNotFound)
		return
	}

	accounts, err := repositories.GetAccountsByUserID(userID)
	if err != nil {
		sendError(w, "Failed to retrieve accounts: "+err.Error(), http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(accounts)
}

// GetAccountHandler retrieves a specific account
func GetAccountHandler(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	accountID, err := strconv.ParseInt(vars["account_id"], 10, 64)
	if err != nil {
		sendError(w, "Invalid account ID", http.StatusBadRequest)
		return
	}

	account, err := repositories.GetAccountByID(accountID)
	if err != nil {
		sendError(w, "Account not found", http.StatusNotFound)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(account)
}

// DepositHandler handles deposits to an account
func DepositHandler(w http.ResponseWriter, r *http.Request) {
	processTransaction(w, r, "deposit")
}

// WithdrawHandler handles withdrawals from an account
func WithdrawHandler(w http.ResponseWriter, r *http.Request) {
	processTransaction(w, r, "withdrawal")
}

// processTransaction handles both deposits and withdrawals
func processTransaction(w http.ResponseWriter, r *http.Request, transactionType string) {
	vars := mux.Vars(r)
	accountID, err := strconv.ParseInt(vars["account_id"], 10, 64)
	if err != nil {
		sendError(w, "Invalid account ID", http.StatusBadRequest)
		return
	}

	var req TransactionRequest
	if err := json.NewDecoder(r.Body).Decode(&req); err != nil {
		sendError(w, "Invalid request body", http.StatusBadRequest)
		return
	}

	// Get the current account
	account, err := repositories.GetAccountByID(accountID)
	if err != nil {
		sendError(w, "Account not found", http.StatusNotFound)
		return
	}

	// Create appropriate account type based on the retrieved account
	var acc models.Account
	if account.AccountType == "Savings" {
		acc = models.NewSavingsAccount(account.Balance)
	} else if account.AccountType == "Current" {
		acc = models.NewCurrentAccount(account.Balance)
	} else {
		sendError(w, "Unknown account type", http.StatusInternalServerError)
		return
	}

	// Perform the transaction
	var txErr error
	if transactionType == "deposit" {
		txErr = acc.Deposit(req.Amount)
	} else { // withdrawal
		txErr = acc.Withdraw(req.Amount)
	}

	if txErr != nil {
		sendError(w, txErr.Error(), http.StatusBadRequest)
		return
	}

	// Update the account balance in the database
	newBalance := acc.CheckBalance()
	if err := repositories.UpdateAccountBalance(accountID, newBalance); err != nil {
		sendError(w, "Failed to update account: "+err.Error(), http.StatusInternalServerError)
		return
	}

	// Record the transaction
	if err := repositories.RecordTransaction(accountID, transactionType, req.Amount); err != nil {
		sendError(w, "Transaction processed but failed to record: "+err.Error(), http.StatusInternalServerError)
		return
	}

	// Get the updated account
	updatedAccount, err := repositories.GetAccountByID(accountID)
	if err != nil {
		sendError(w, "Failed to retrieve updated account", http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(updatedAccount)
}

// GetTransactionHistoryHandler retrieves transaction history for an account
func GetTransactionHistoryHandler(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	accountID, err := strconv.ParseInt(vars["account_id"], 10, 64)
	if err != nil {
		sendError(w, "Invalid account ID", http.StatusBadRequest)
		return
	}

	// Check if the account exists
	_, err = repositories.GetAccountByID(accountID)
	if err != nil {
		sendError(w, "Account not found", http.StatusNotFound)
		return
	}

	transactions, err := repositories.GetTransactionHistory(accountID)
	if err != nil {
		sendError(w, "Failed to retrieve transactions: "+err.Error(), http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(transactions)
}

// sendError sends an error response
func sendError(w http.ResponseWriter, message string, statusCode int) {
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(statusCode)
	json.NewEncoder(w).Encode(ErrorResponse{Error: message})
}
