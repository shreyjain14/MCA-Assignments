package main

// Product represents a product in the ecommerce store
type Product struct {
	ID          int     `json:"id"`
	Name        string  `json:"name"`
	Description string  `json:"description"`
	Price       float64 `json:"price"`
	Stock       int     `json:"stock"`
	Category    string  `json:"category"`
	Quantity    int     // For shopping cart
}

// Products represents the collection of products
type Products struct {
	Products []Product `json:"products"`
}

// Cart represents the shopping cart
type Cart struct {
	Items      []Product
	TotalItems int
	TotalPrice float64
}
