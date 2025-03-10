package models

import (
	"errors"
	"time"
)

// Account interface defines methods required for all account types
type Account interface {
	Deposit(amount float64) error
	Withdraw(amount float64) error
	CheckBalance() float64
	AccountType() string
}

// Transaction represents a single transaction
type Transaction struct {
	ID        int64     `json:"id"`
	AccountID int64     `json:"account_id"`
	Type      string    `json:"type"` // "deposit" or "withdrawal"
	Amount    float64   `json:"amount"`
	Timestamp time.Time `json:"timestamp"`
}

// AccountModel represents the data to store in the database
type AccountModel struct {
	ID           int64         `json:"id"`
	UserID       int64         `json:"user_id"`
	AccountType  string        `json:"account_type"`
	Balance      float64       `json:"balance"`
	Transactions []Transaction `json:"transactions,omitempty"`
}

// SavingsAccount implements the Account interface
type SavingsAccount struct {
	balance       *float64
	withdrawLimit float64
}

// CurrentAccount implements the Account interface
type CurrentAccount struct {
	balance        *float64
	overdraftLimit float64
}

// NewSavingsAccount creates a new savings account with the initial balance
func NewSavingsAccount(initialBalance float64) *SavingsAccount {
	balance := initialBalance
	return &SavingsAccount{
		balance:       &balance,
		withdrawLimit: 500.0,
	}
}

// NewCurrentAccount creates a new current account with the initial balance
func NewCurrentAccount(initialBalance float64) *CurrentAccount {
	balance := initialBalance
	return &CurrentAccount{
		balance:        &balance,
		overdraftLimit: 200.0,
	}
}

// Deposit adds money to the savings account
func (s *SavingsAccount) Deposit(amount float64) error {
	if amount <= 0 {
		return errors.New("deposit amount must be positive")
	}
	*s.balance += amount
	return nil
}

// Withdraw takes money from the savings account
func (s *SavingsAccount) Withdraw(amount float64) error {
	if amount <= 0 {
		return errors.New("withdrawal amount must be positive")
	}
	if amount > s.withdrawLimit {
		return errors.New("withdrawal amount exceeds the limit of 500")
	}
	if amount > *s.balance {
		return errors.New("insufficient funds")
	}
	*s.balance -= amount
	return nil
}

// CheckBalance returns the current balance
func (s *SavingsAccount) CheckBalance() float64 {
	return *s.balance
}

// AccountType returns the type of account
func (s *SavingsAccount) AccountType() string {
	return "Savings"
}

// Deposit adds money to the current account
func (c *CurrentAccount) Deposit(amount float64) error {
	if amount <= 0 {
		return errors.New("deposit amount must be positive")
	}
	*c.balance += amount
	return nil
}

// Withdraw takes money from the current account
func (c *CurrentAccount) Withdraw(amount float64) error {
	if amount <= 0 {
		return errors.New("withdrawal amount must be positive")
	}

	// Allow overdraft up to the limit
	if amount > *c.balance+c.overdraftLimit {
		return errors.New("withdrawal exceeds overdraft limit")
	}

	*c.balance -= amount
	return nil
}

// CheckBalance returns the current balance
func (c *CurrentAccount) CheckBalance() float64 {
	return *c.balance
}

// AccountType returns the type of account
func (c *CurrentAccount) AccountType() string {
	return "Current"
}

// User represents a user in the system
type User struct {
	ID       int64  `json:"id"`
	Username string `json:"username"`
	Email    string `json:"email"`
}
