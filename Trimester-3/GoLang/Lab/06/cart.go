package main

// ShoppingCart is a concrete implementation of the Cart interface
type ShoppingCart struct {
	items map[Product]int
}

// NewCart creates a new shopping cart
func NewCart() Cart {
	return &ShoppingCart{
		items: make(map[Product]int),
	}
}

// AddProduct adds a product to the cart
func (c *ShoppingCart) AddProduct(p Product, quantity int) {
	if quantity <= 0 {
		return
	}

	if _, exists := c.items[p]; exists {
		c.items[p] += quantity
	} else {
		c.items[p] = quantity
	}
}

// RemoveProduct removes a product from the cart
func (c *ShoppingCart) RemoveProduct(productID int) {
	for p := range c.items {
		if p.ID() == productID {
			delete(c.items, p)
			return
		}
	}
}

// UpdateQuantity updates the quantity of a product in the cart
func (c *ShoppingCart) UpdateQuantity(productID int, quantity int) {
	if quantity <= 0 {
		c.RemoveProduct(productID)
		return
	}

	for p := range c.items {
		if p.ID() == productID {
			c.items[p] = quantity
			return
		}
	}
}

// GetItems returns all items in the cart
func (c *ShoppingCart) GetItems() map[Product]int {
	return c.items
}

// TotalPrice calculates the total price of all items in the cart
func (c *ShoppingCart) TotalPrice() float64 {
	total := 0.0
	for p, qty := range c.items {
		total += p.Price() * float64(qty)
	}
	return total
}

// Clear empties the cart
func (c *ShoppingCart) Clear() {
	c.items = make(map[Product]int)
}
