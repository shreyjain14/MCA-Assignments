package main

// Product interface defines the behavior of a product
type Product interface {
	ID() int
	Name() string
	Price() float64
	Category() string
	Description() string
}

// Cart interface defines the behavior of a shopping cart
type Cart interface {
	AddProduct(p Product, quantity int)
	RemoveProduct(productID int)
	UpdateQuantity(productID int, quantity int)
	GetItems() map[Product]int
	TotalPrice() float64
	Clear()
}
