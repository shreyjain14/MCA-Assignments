package main

import (
	"database/sql"
	"log"

	_ "github.com/mattn/go-sqlite3"
)

var db *sql.DB

func initDB() {
	var err error
	db, err = sql.Open("sqlite3", "./ecommerce.db")
	if err != nil {
		log.Fatal(err)
	}

	// Create products table
	createTableSQL := `
	CREATE TABLE IF NOT EXISTS products (
		id INTEGER PRIMARY KEY AUTOINCREMENT,
		name TEXT NOT NULL,
		description TEXT,
		price REAL NOT NULL,
		stock INTEGER NOT NULL,
		created_at DATETIME DEFAULT CURRENT_TIMESTAMP
	);`

	_, err = db.Exec(createTableSQL)
	if err != nil {
		log.Fatal(err)
	}

	// Insert some sample data
	insertSampleData()
}

func insertSampleData() {
	// Check if data already exists
	var count int
	err := db.QueryRow("SELECT COUNT(*) FROM products").Scan(&count)
	if err != nil {
		log.Fatal(err)
	}

	if count == 0 {
		// Insert sample products
		products := []Product{
			{Name: "Laptop", Description: "High-performance laptop", Price: 999.99, Stock: 10},
			{Name: "Smartphone", Description: "Latest smartphone", Price: 699.99, Stock: 15},
			{Name: "Headphones", Description: "Wireless headphones", Price: 199.99, Stock: 20},
		}

		for _, p := range products {
			_, err := db.Exec(
				"INSERT INTO products (name, description, price, stock) VALUES (?, ?, ?, ?)",
				p.Name, p.Description, p.Price, p.Stock,
			)
			if err != nil {
				log.Fatal(err)
			}
		}
	}
}

func getAllProducts() ([]Product, error) {
	rows, err := db.Query("SELECT id, name, description, price, stock, created_at FROM products")
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	var products []Product
	for rows.Next() {
		var p Product
		err := rows.Scan(&p.ID, &p.Name, &p.Description, &p.Price, &p.Stock, &p.CreatedAt)
		if err != nil {
			return nil, err
		}
		products = append(products, p)
	}
	return products, nil
}

func getProductByID(id int) (*Product, error) {
	var p Product
	err := db.QueryRow("SELECT id, name, description, price, stock, created_at FROM products WHERE id = ?", id).
		Scan(&p.ID, &p.Name, &p.Description, &p.Price, &p.Stock, &p.CreatedAt)
	if err != nil {
		return nil, err
	}
	return &p, nil
}

func addProduct(p *Product) error {
	result, err := db.Exec(
		"INSERT INTO products (name, description, price, stock) VALUES (?, ?, ?, ?)",
		p.Name, p.Description, p.Price, p.Stock,
	)
	if err != nil {
		return err
	}

	id, err := result.LastInsertId()
	if err != nil {
		return err
	}
	p.ID = int(id)
	return nil
}

func updateProductPrice(id int, price float64) error {
	_, err := db.Exec("UPDATE products SET price = ? WHERE id = ?", price, id)
	return err
}

func updateProductStock(id int, stock int) error {
	_, err := db.Exec("UPDATE products SET stock = ? WHERE id = ?", stock, id)
	return err
}
