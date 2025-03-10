package db

import (
	"database/sql"
	"fmt"
	"log"

	_ "github.com/mattn/go-sqlite3"
)

// DB is the database connection
var DB *sql.DB

// InitDB initializes the database connection and creates tables if they don't exist
func InitDB() error {
	var err error
	DB, err = sql.Open("sqlite3", "./banking.db")
	if err != nil {
		return fmt.Errorf("failed to open database: %w", err)
	}

	// Test the connection
	if err = DB.Ping(); err != nil {
		return fmt.Errorf("failed to ping database: %w", err)
	}

	// Create tables
	err = createTables()
	if err != nil {
		return fmt.Errorf("failed to create tables: %w", err)
	}

	log.Println("Database initialized successfully")
	return nil
}

// createTables creates necessary tables for the application
func createTables() error {
	// Users table
	userTableQuery := `
	CREATE TABLE IF NOT EXISTS users (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		username TEXT NOT NULL UNIQUE,
		email TEXT NOT NULL UNIQUE
	);`

	_, err := DB.Exec(userTableQuery)
	if err != nil {
		return err
	}

	// Accounts table
	accountsTableQuery := `
	CREATE TABLE IF NOT EXISTS accounts (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		user_id INTEGER NOT NULL,
		account_type TEXT NOT NULL,
		balance REAL NOT NULL,
		FOREIGN KEY (user_id) REFERENCES users (id)
	);`

	_, err = DB.Exec(accountsTableQuery)
	if err != nil {
		return err
	}

	// Transactions table
	transactionsTableQuery := `
	CREATE TABLE IF NOT EXISTS transactions (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		account_id INTEGER NOT NULL,
		type TEXT NOT NULL,
		amount REAL NOT NULL,
		timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
		FOREIGN KEY (account_id) REFERENCES accounts (id)
	);`

	_, err = DB.Exec(transactionsTableQuery)
	if err != nil {
		return err
	}

	return nil
}
