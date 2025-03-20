package main

import (
	"fmt"
	"time"
)

// Product represents an item in our e-commerce store
type Product struct {
	ID          int       `json:"id"`
	Name        string    `json:"name"`
	Description string    `json:"description"`
	Price       float64   `json:"price"`
	Stock       int       `json:"stock"`
	CreatedAt   time.Time `json:"created_at"`
}

// Demonstrating pointer concepts
// Call by value example
func (p Product) UpdatePriceByValue(newPrice float64) {
	p.Price = newPrice // This won't affect the original product
}

// Call by reference example
func (p *Product) UpdatePriceByReference(newPrice float64) {
	p.Price = newPrice // This will affect the original product
}

// Pointer demonstration function
func DemonstratePointers() {
	// Create a product
	product := Product{
		ID:          1,
		Name:        "Test Product",
		Description: "Test Description",
		Price:       99.99,
		Stock:       10,
		CreatedAt:   time.Now(),
	}

	// Call by value demonstration
	originalPrice := product.Price
	product.UpdatePriceByValue(149.99)
	fmt.Printf("After UpdatePriceByValue: Original Price: %.2f, Current Price: %.2f\n", originalPrice, product.Price)

	// Call by reference demonstration
	product.UpdatePriceByReference(149.99)
	fmt.Printf("After UpdatePriceByReference: Price: %.2f\n", product.Price)

	// Pointer to product demonstration
	productPtr := &product
	productPtr.Price = 199.99 // This will modify the original product
	fmt.Printf("After pointer modification: Price: %.2f\n", product.Price)
}
