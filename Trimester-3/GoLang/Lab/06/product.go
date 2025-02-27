package main

// BasicProduct is a concrete implementation of the Product interface
type BasicProduct struct {
	id          int
	name        string
	price       float64
	category    string
	description string
}

// ID returns the product ID
func (p BasicProduct) ID() int {
	return p.id
}

// Name returns the product name
func (p BasicProduct) Name() string {
	return p.name
}

// Price returns the product price
func (p BasicProduct) Price() float64 {
	return p.price
}

// Category returns the product category
func (p BasicProduct) Category() string {
	return p.category
}

// Description returns the product description
func (p BasicProduct) Description() string {
	return p.description
}

// NewProduct creates a new product
func NewProduct(id int, name string, price float64, category, description string) Product {
	return BasicProduct{
		id:          id,
		name:        name,
		price:       price,
		category:    category,
		description: description,
	}
}
