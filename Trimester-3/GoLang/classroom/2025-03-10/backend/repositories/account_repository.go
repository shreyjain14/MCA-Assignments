package repositories

import (
	"fmt"
	"time"

	"banking/db"
	"banking/models"
)

// CreateUser creates a new user in the database
func CreateUser(username, email string) (int64, error) {
	stmt, err := db.DB.Prepare("INSERT INTO users (username, email) VALUES (?, ?)")
	if err != nil {
		return 0, err
	}
	defer stmt.Close()

	result, err := stmt.Exec(username, email)
	if err != nil {
		return 0, err
	}

	id, err := result.LastInsertId()
	if err != nil {
		return 0, err
	}

	return id, nil
}

// GetUserByID retrieves a user by ID
func GetUserByID(id int64) (*models.User, error) {
	row := db.DB.QueryRow("SELECT id, username, email FROM users WHERE id = ?", id)

	var user models.User
	err := row.Scan(&user.ID, &user.Username, &user.Email)
	if err != nil {
		return nil, err
	}

	return &user, nil
}

// CreateAccount creates a new account for a user
func CreateAccount(userID int64, accountType string, initialBalance float64) (int64, error) {
	stmt, err := db.DB.Prepare("INSERT INTO accounts (user_id, account_type, balance) VALUES (?, ?, ?)")
	if err != nil {
		return 0, err
	}
	defer stmt.Close()

	result, err := stmt.Exec(userID, accountType, initialBalance)
	if err != nil {
		return 0, err
	}

	return result.LastInsertId()
}

// GetAccountByID retrieves an account by ID
func GetAccountByID(id int64) (*models.AccountModel, error) {
	row := db.DB.QueryRow("SELECT id, user_id, account_type, balance FROM accounts WHERE id = ?", id)

	var account models.AccountModel
	err := row.Scan(&account.ID, &account.UserID, &account.AccountType, &account.Balance)
	if err != nil {
		return nil, err
	}

	return &account, nil
}

// GetAccountsByUserID retrieves all accounts for a user
func GetAccountsByUserID(userID int64) ([]models.AccountModel, error) {
	rows, err := db.DB.Query("SELECT id, user_id, account_type, balance FROM accounts WHERE user_id = ?", userID)
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	var accounts []models.AccountModel
	for rows.Next() {
		var account models.AccountModel
		err := rows.Scan(&account.ID, &account.UserID, &account.AccountType, &account.Balance)
		if err != nil {
			return nil, err
		}
		accounts = append(accounts, account)
	}

	return accounts, nil
}

// UpdateAccountBalance updates the balance of an account
func UpdateAccountBalance(id int64, newBalance float64) error {
	stmt, err := db.DB.Prepare("UPDATE accounts SET balance = ? WHERE id = ?")
	if err != nil {
		return err
	}
	defer stmt.Close()

	_, err = stmt.Exec(newBalance, id)
	return err
}

// RecordTransaction records a new transaction
func RecordTransaction(accountID int64, transactionType string, amount float64) error {
	stmt, err := db.DB.Prepare("INSERT INTO transactions (account_id, type, amount) VALUES (?, ?, ?)")
	if err != nil {
		return err
	}
	defer stmt.Close()

	_, err = stmt.Exec(accountID, transactionType, amount)
	return err
}

// GetTransactionHistory retrieves transaction history for an account
func GetTransactionHistory(accountID int64) ([]models.Transaction, error) {
	rows, err := db.DB.Query("SELECT id, account_id, type, amount, timestamp FROM transactions WHERE account_id = ? ORDER BY timestamp DESC", accountID)
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	var transactions []models.Transaction
	for rows.Next() {
		var transaction models.Transaction
		var timestamp string
		err := rows.Scan(&transaction.ID, &transaction.AccountID, &transaction.Type, &transaction.Amount, &timestamp)
		if err != nil {
			return nil, err
		}

		transaction.Timestamp, err = time.Parse("2006-01-02 15:04:05", timestamp)
		if err != nil {
			return nil, fmt.Errorf("error parsing timestamp: %w", err)
		}

		transactions = append(transactions, transaction)
	}

	return transactions, nil
}
