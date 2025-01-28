package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

func main() {
	var productID int
	var quantity int
	var price float64
	var productName string
	var category string

	reader := bufio.NewReader(os.Stdin)

	for {
		fmt.Print("Enter Product ID: ")
		_, err := fmt.Scanf("%d\n", &productID)
		if err != nil {
			fmt.Println("Please enter a valid integer for Product ID")
			fmt.Scanln()
			continue
		}
		break
	}

	fmt.Print("Enter Product Name: ")
	productName, _ = reader.ReadString('\n')
	productName = strings.TrimSpace(productName)

	fmt.Print("Enter Product Category: ")
	category, _ = reader.ReadString('\n')
	category = strings.TrimSpace(category)

	for {
		fmt.Print("Enter Price: ")
		_, err := fmt.Scanf("%f\n", &price)
		if err != nil {
			fmt.Println("Please enter a valid float for Price")
			fmt.Scanln()
			continue
		}
		break
	}

	for {
		fmt.Print("Enter Quantity: ")
		_, err := fmt.Scanf("%d\n", &quantity)
		if err != nil {
			fmt.Println("Please enter a valid integer for Quantity")
			fmt.Scanln()
			continue
		}
		break
	}

	if quantity > 0 {
		fmt.Println("Product is available")
	} else {
		fmt.Println("Product is out of stock")
	}

	for i := 0; i < quantity; i++ {
		fmt.Printf("Processing item %d\n", i+1)
	}

	switch category {
	case "Clothing":
		fmt.Println("This is a clothing item")
	case "Electronics":
		fmt.Println("This is an electronic item")
	default:
		fmt.Println("Unknown category")
	}

	fmt.Printf("\nProduct Details:\n")
	fmt.Printf("ID: %d\n", productID)
	fmt.Printf("Name: %s\n", productName)
	fmt.Printf("Category: %s\n", category)
	fmt.Printf("Price: %.2f\n", price)
	fmt.Printf("Quantity: %d\n", quantity)
}
