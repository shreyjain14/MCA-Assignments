package main

import (
    "bufio"
    "fmt"
    "os"
    "strings"
)

type Product struct {
    ID       int
    Name     string
    Category string
    Price    float64
    Quantity int
}

var products []Product
var reader = bufio.NewReader(os.Stdin)

func clearScreen() {
    fmt.Print("\033[H\033[2J")
}

func getInput(prompt string) string {
    fmt.Print(prompt)
    input, _ := reader.ReadString('\n')
    return strings.TrimSpace(input)
}

func addProduct() {
    var product Product

    fmt.Println("\n=== Add New Product ===")
    
    for {
        fmt.Print("Enter Product ID: ")
        if _, err := fmt.Scanf("%d\n", &product.ID); err != nil {
            fmt.Println("Please enter a valid integer for Product ID")
            fmt.Scanln()
            continue
        }
        break
    }

    product.Name = getInput("Enter Product Name: ")
    product.Category = getInput("Enter Category: ")

    for {
        fmt.Print("Enter Price: ")
        if _, err := fmt.Scanf("%f\n", &product.Price); err != nil {
            fmt.Println("Please enter a valid price")
            fmt.Scanln()
            continue
        }
        break
    }

    for {
        fmt.Print("Enter Quantity: ")
        if _, err := fmt.Scanf("%d\n", &product.Quantity); err != nil {
            fmt.Println("Please enter a valid quantity")
            fmt.Scanln()
            continue
        }
        break
    }

    products = append(products, product)
    fmt.Println("\nProduct added successfully!")
}

func viewProducts() {
    if len(products) == 0 {
        fmt.Println("\nNo products available.")
        return
    }

    fmt.Println("\n=== Product List ===")
    fmt.Println("ID\tName\t\tCategory\tPrice\tQuantity")
    fmt.Println("------------------------------------------------")
    for _, p := range products {
        fmt.Printf("%d\t%s\t\t%s\t\t%.2f\t%d\n", p.ID, p.Name, p.Category, p.Price, p.Quantity)
    }
}

func displayMenu() {
    fmt.Println("\n=== Product Management System ===")
    fmt.Println("1. Add New Product")
    fmt.Println("2. View All Products")
    fmt.Println("3. Exit")
    fmt.Print("Enter your choice: ")
}

func main() {
    for {
        displayMenu()
        
        var choice int
        if _, err := fmt.Scanf("%d\n", &choice); err != nil {
            fmt.Println("Please enter a valid choice")
            fmt.Scanln()
            continue
        }

        switch choice {
        case 1:
            addProduct()
        case 2:
            viewProducts()
        case 3:
            fmt.Println("Goodbye!")
            return
        default:
            fmt.Println("Invalid choice. Please try again.")
        }
    }
}