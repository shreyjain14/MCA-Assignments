package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

// UI handles user interaction
type UI struct {
	cart     Cart
	products []Product
	scanner  *bufio.Scanner
}

// NewUI creates a new UI
func NewUI(cart Cart, products []Product) *UI {
	return &UI{
		cart:     cart,
		products: products,
		scanner:  bufio.NewScanner(os.Stdin),
	}
}

// Run starts the UI
func (ui *UI) Run() {
	for {
		ui.showMenu()
		choice := ui.getInput("Enter your choice: ")

		switch choice {
		case "1":
			ui.showProducts()
		case "2":
			ui.addToCart()
		case "3":
			ui.viewCart()
		case "4":
			ui.updateQuantity()
		case "5":
			ui.removeFromCart()
		case "6":
			ui.checkout()
		case "7":
			fmt.Println("Thank you for shopping with us!")
			return
		default:
			fmt.Println("Invalid choice. Please try again.")
		}
	}
}

func (ui *UI) showMenu() {
	fmt.Println("\n======== E-commerce App ========")
	fmt.Println("1. View Products")
	fmt.Println("2. Add to Cart")
	fmt.Println("3. View Cart")
	fmt.Println("4. Update Quantity")
	fmt.Println("5. Remove from Cart")
	fmt.Println("6. Checkout")
	fmt.Println("7. Exit")
	fmt.Println("===============================")
}

func (ui *UI) showProducts() {
	fmt.Println("\n======== Available Products ========")
	for _, p := range ui.products {
		fmt.Printf("ID: %d | %s | $%.2f | %s\n", p.ID(), p.Name(), p.Price(), p.Category())
	}
	fmt.Println("===================================")
}

func (ui *UI) addToCart() {
	ui.showProducts()
	idStr := ui.getInput("Enter product ID to add to cart: ")
	qtyStr := ui.getInput("Enter quantity: ")

	id, err1 := strconv.Atoi(idStr)
	qty, err2 := strconv.Atoi(qtyStr)

	if err1 != nil || err2 != nil || qty <= 0 {
		fmt.Println("Invalid input. Please enter valid numbers.")
		return
	}

	for _, p := range ui.products {
		if p.ID() == id {
			ui.cart.AddProduct(p, qty)
			fmt.Printf("Added %d x %s to cart.\n", qty, p.Name())
			return
		}
	}

	fmt.Println("Product not found.")
}

func (ui *UI) viewCart() {
	items := ui.cart.GetItems()
	if len(items) == 0 {
		fmt.Println("Your cart is empty.")
		return
	}

	fmt.Println("\n======== Your Cart ========")
	for p, qty := range items {
		fmt.Printf("ID: %d | %s | $%.2f x %d = $%.2f\n",
			p.ID(), p.Name(), p.Price(), qty, p.Price()*float64(qty))
	}
	fmt.Printf("Total: $%.2f\n", ui.cart.TotalPrice())
	fmt.Println("==========================")
}

func (ui *UI) updateQuantity() {
	ui.viewCart()
	if len(ui.cart.GetItems()) == 0 {
		return
	}

	idStr := ui.getInput("Enter product ID to update: ")
	qtyStr := ui.getInput("Enter new quantity (0 to remove): ")

	id, err1 := strconv.Atoi(idStr)
	qty, err2 := strconv.Atoi(qtyStr)

	if err1 != nil || err2 != nil || qty < 0 {
		fmt.Println("Invalid input. Please enter valid numbers.")
		return
	}

	ui.cart.UpdateQuantity(id, qty)
	fmt.Println("Cart updated.")
}

func (ui *UI) removeFromCart() {
	ui.viewCart()
	if len(ui.cart.GetItems()) == 0 {
		return
	}

	idStr := ui.getInput("Enter product ID to remove: ")
	id, err := strconv.Atoi(idStr)

	if err != nil {
		fmt.Println("Invalid input. Please enter a valid number.")
		return
	}

	ui.cart.RemoveProduct(id)
	fmt.Println("Product removed from cart.")
}

func (ui *UI) checkout() {
	if len(ui.cart.GetItems()) == 0 {
		fmt.Println("Your cart is empty.")
		return
	}

	fmt.Println("\n======== Checkout ========")
	ui.viewCart()

	confirm := ui.getInput("Confirm order (y/n): ")
	if strings.ToLower(confirm) == "y" {
		fmt.Println("Order confirmed! Thank you for your purchase.")
		ui.cart.Clear()
	} else {
		fmt.Println("Checkout cancelled.")
	}
}

func (ui *UI) getInput(prompt string) string {
	fmt.Print(prompt)
	ui.scanner.Scan()
	return ui.scanner.Text()
}
