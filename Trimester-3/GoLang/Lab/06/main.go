package main

import (
	"fmt"
	"strconv"
)

type Product interface {
	GetID() string
	GetName() string
	GetPrice() float64
	GetDescription() string
	Display()
}

type BaseProduct struct {
	ID          string
	Name        string
	Price       float64
	Description string
}

func (p BaseProduct) GetID() string {
	return p.ID
}

func (p BaseProduct) GetName() string {
	return p.Name
}

func (p BaseProduct) GetPrice() float64 {
	return p.Price
}

func (p BaseProduct) GetDescription() string {
	return p.Description
}

func (p BaseProduct) Display() {
	fmt.Printf("Product: %s\nPrice: $%.2f\nDescription: %s\n", p.Name, p.Price, p.Description)
}

type Electronics struct {
	BaseProduct
	Warranty    int
	PowerRating int
}

func (e Electronics) Display() {
	e.BaseProduct.Display()
	fmt.Printf("Warranty: %d months\nPower Rating: %dW\n\n", e.Warranty, e.PowerRating)
}

type Clothing struct {
	BaseProduct
	Size  string
	Color string
}

func (c Clothing) Display() {
	c.BaseProduct.Display()
	fmt.Printf("Size: %s\nColor: %s\n\n", c.Size, c.Color)
}

type Cart interface {
	AddItem(p Product, quantity int)
	RemoveItem(productID string)
	UpdateQuantity(productID string, quantity int)
	GetTotal() float64
	DisplayItems()
}

type ShoppingCart struct {
	Items map[string]CartItem
}

type CartItem struct {
	Product  Product
	Quantity int
}

func NewShoppingCart() *ShoppingCart {
	return &ShoppingCart{
		Items: make(map[string]CartItem),
	}
}

func (cart *ShoppingCart) AddItem(p Product, quantity int) {
	if quantity <= 0 {
		return
	}

	id := p.GetID()
	if item, exists := cart.Items[id]; exists {
		item.Quantity += quantity
		cart.Items[id] = item
	} else {
		cart.Items[id] = CartItem{
			Product:  p,
			Quantity: quantity,
		}
	}
	fmt.Printf("Added %d x %s to cart\n", quantity, p.GetName())
}

func (cart *ShoppingCart) RemoveItem(productID string) {
	if _, exists := cart.Items[productID]; exists {
		delete(cart.Items, productID)
		fmt.Println("Item removed from cart")
	}
}

func (cart *ShoppingCart) UpdateQuantity(productID string, quantity int) {
	if item, exists := cart.Items[productID]; exists {
		if quantity <= 0 {
			cart.RemoveItem(productID)
		} else {
			item.Quantity = quantity
			cart.Items[productID] = item
			fmt.Printf("Updated quantity for %s to %d\n", item.Product.GetName(), quantity)
		}
	}
}

func (cart *ShoppingCart) GetTotal() float64 {
	var total float64
	for _, item := range cart.Items {
		total += item.Product.GetPrice() * float64(item.Quantity)
	}
	return total
}

func (cart *ShoppingCart) DisplayItems() {
	if len(cart.Items) == 0 {
		fmt.Println("Your cart is empty")
		return
	}

	fmt.Println("\n===== SHOPPING CART =====")
	for _, item := range cart.Items {
		fmt.Printf("%s - $%.2f x %d = $%.2f\n",
			item.Product.GetName(),
			item.Product.GetPrice(),
			item.Quantity,
			item.Product.GetPrice()*float64(item.Quantity))
	}
	fmt.Printf("=======================\nTotal: $%.2f\n\n", cart.GetTotal())
}

type Payment interface {
	ProcessPayment(amount float64) bool
	DisplayPaymentInfo()
}

type CreditCard struct {
	CardNumber string
	CVV        string
	ExpiryDate string
	CardHolder string
}

func (cc CreditCard) ProcessPayment(amount float64) bool {
	fmt.Printf("Processing credit card payment for $%.2f...\n", amount)
	fmt.Println("Payment successful!")
	return true
}

func (cc CreditCard) DisplayPaymentInfo() {
	maskedNumber := cc.CardNumber[len(cc.CardNumber)-4:]
	fmt.Printf("Credit Card: **** **** **** %s\nCard Holder: %s\n", maskedNumber, cc.CardHolder)
}

type PayPal struct {
	Email    string
	Password string
}

func (pp PayPal) ProcessPayment(amount float64) bool {
	fmt.Printf("Processing PayPal payment for $%.2f...\n", amount)
	fmt.Println("Payment successful!")
	return true
}

func (pp PayPal) DisplayPaymentInfo() {
	fmt.Printf("PayPal Account: %s\n", pp.Email)
}

type Order struct {
	OrderID       string
	Items         []CartItem
	TotalAmount   float64
	PaymentMethod Payment
}

func CreateOrder(cart *ShoppingCart, payment Payment) *Order {
	items := make([]CartItem, 0, len(cart.Items))
	for _, item := range cart.Items {
		items = append(items, item)
	}

	return &Order{
		OrderID:       "ORD-" + strconv.FormatInt(12345, 10),
		Items:         items,
		TotalAmount:   cart.GetTotal(),
		PaymentMethod: payment,
	}
}

func (o *Order) Checkout() bool {
	fmt.Printf("\n===== PROCESSING ORDER #%s =====\n", o.OrderID)
	for _, item := range o.Items {
		fmt.Printf("%d x %s\n", item.Quantity, item.Product.GetName())
	}
	fmt.Printf("Total: $%.2f\n", o.TotalAmount)
	o.PaymentMethod.DisplayPaymentInfo()

	return o.PaymentMethod.ProcessPayment(o.TotalAmount)
}

func main() {
	laptop := Electronics{
		BaseProduct: BaseProduct{
			ID:          "E001",
			Name:        "MacBook Pro",
			Price:       1299.99,
			Description: "13-inch, 8GB RAM, 256GB SSD",
		},
		Warranty:    24,
		PowerRating: 60,
	}

	phone := Electronics{
		BaseProduct: BaseProduct{
			ID:          "E002",
			Name:        "iPhone 13",
			Price:       999.99,
			Description: "128GB, Midnight Blue",
		},
		Warranty:    12,
		PowerRating: 20,
	}

	tshirt := Clothing{
		BaseProduct: BaseProduct{
			ID:          "C001",
			Name:        "Cotton T-Shirt",
			Price:       19.99,
			Description: "100% organic cotton",
		},
		Size:  "L",
		Color: "Black",
	}

	fmt.Println("===== AVAILABLE PRODUCTS =====")
	laptop.Display()
	phone.Display()
	tshirt.Display()

	cart := NewShoppingCart()

	cart.AddItem(laptop, 1)
	cart.AddItem(phone, 2)
	cart.AddItem(tshirt, 3)

	cart.DisplayItems()

	cart.UpdateQuantity("E002", 1)

	cart.RemoveItem("C001")

	cart.DisplayItems()

	creditCard := CreditCard{
		CardNumber: "1234567890123456",
		CVV:        "123",
		ExpiryDate: "12/25",
		CardHolder: "John Doe",
	}

	order := CreateOrder(cart, creditCard)
	order.Checkout()
}
