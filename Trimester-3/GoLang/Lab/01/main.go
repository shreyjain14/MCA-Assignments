package main

import "fmt"

func main() {
	var productID int
	var quantity int
	var price float64
	var productName string
	var category string
	var inStock bool

	fmt.Print("Enter Product ID: ")
	fmt.Scan(&productID)

	fmt.Print("Enter Product Name: ")
	fmt.Scan(&productName)

	fmt.Print("Enter Product Category: ")
	fmt.Scan(&category)

	fmt.Print("Enter Price: ")
	fmt.Scan(&price)

	fmt.Print("Enter Quantity: ")
	fmt.Scan(&quantity)

	fmt.Print("Is product in stock (true/false): ")
	fmt.Scan(&inStock)

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
}
