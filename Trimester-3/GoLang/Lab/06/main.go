package main

func main() {
	// Create products
	products := []Product{
		NewProduct(1, "Smartphone", 499.99, "Electronics", "Latest smartphone with high-end features"),
		NewProduct(2, "Laptop", 899.99, "Electronics", "Powerful laptop for work and gaming"),
		NewProduct(3, "T-shirt", 19.99, "Clothing", "Comfortable cotton t-shirt"),
		NewProduct(4, "Jeans", 39.99, "Clothing", "Stylish denim jeans"),
		NewProduct(5, "Book", 12.99, "Books", "Bestselling novel"),
		NewProduct(6, "Headphones", 79.99, "Electronics", "Wireless noise-cancelling headphones"),
	}

	// Create cart
	cart := NewCart()

	// Create and start UI
	ui := NewUI(cart, products)
	ui.Run()
}
