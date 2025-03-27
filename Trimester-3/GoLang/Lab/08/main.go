package main

import (
	"encoding/json"
	"fmt"
	"log"
	"os"
	"strconv"

	"fyne.io/fyne/v2"
	"fyne.io/fyne/v2/app"
	"fyne.io/fyne/v2/container"
	"fyne.io/fyne/v2/dialog"
	"fyne.io/fyne/v2/layout"
	"fyne.io/fyne/v2/theme"
	"fyne.io/fyne/v2/widget"
)

var (
	productList          Products
	cart                 Cart
	mainWindow           fyne.Window
	globalCartContent    *fyne.Container
	globalTotalLabel     *widget.Label
	globalItemCountLabel *widget.Label
)

func main() {
	// Create new app
	a := app.New()
	mainWindow = a.NewWindow("Go Ecommerce Shop")
	mainWindow.Resize(fyne.NewSize(800, 600))

	// Load products from JSON
	err := loadProductsFromJSON("data.json")
	if err != nil {
		dialog.ShowError(err, mainWindow)
		log.Fatalf("Failed to load products: %v", err)
	}

	// Initialize cart
	cart = Cart{
		Items:      []Product{},
		TotalItems: 0,
		TotalPrice: 0,
	}

	// Create UI tabs
	tabs := container.NewAppTabs(
		container.NewTabItemWithIcon("Products", theme.HomeIcon(), createProductListTab()),
		container.NewTabItemWithIcon("Cart", theme.ContentAddIcon(), createCartTab()),
	)

	mainWindow.SetContent(tabs)
	mainWindow.ShowAndRun()
}

// Load products from JSON file
func loadProductsFromJSON(filename string) error {
	data, err := os.ReadFile(filename)
	if err != nil {
		return fmt.Errorf("error reading file: %v", err)
	}

	err = json.Unmarshal(data, &productList)
	if err != nil {
		return fmt.Errorf("error parsing JSON: %v", err)
	}

	return nil
}

// Save products to JSON file
func saveProductsToJSON(filename string) error {
	data, err := json.MarshalIndent(productList, "", "  ")
	if err != nil {
		return fmt.Errorf("error marshaling products: %v", err)
	}

	err = os.WriteFile(filename, data, 0644)
	if err != nil {
		return fmt.Errorf("error writing file: %v", err)
	}

	return nil
}

// Create the product list tab
func createProductListTab() fyne.CanvasObject {
	productListContainer := container.NewVBox()

	// Create header
	header := widget.NewLabelWithStyle("Available Products", fyne.TextAlignCenter, fyne.TextStyle{Bold: true})
	productListContainer.Add(header)

	// Create list of products
	for i, product := range productList.Products {
		// Create a copy of the product for the closure
		p := product
		index := i

		// Create product card
		card := createProductCard(p, index)
		productListContainer.Add(card)
	}

	// Wrap in scroll container
	return container.NewScroll(productListContainer)
}

// Create a card for a product
func createProductCard(product Product, index int) fyne.CanvasObject {
	// Product info
	nameLabel := widget.NewLabelWithStyle(product.Name, fyne.TextAlignLeading, fyne.TextStyle{Bold: true})
	descLabel := widget.NewLabel(product.Description)
	priceLabel := widget.NewLabel(fmt.Sprintf("Price: $%.2f", product.Price))
	stockLabel := widget.NewLabel(fmt.Sprintf("In Stock: %d", product.Stock))
	categoryLabel := widget.NewLabel(fmt.Sprintf("Category: %s", product.Category))

	// Quantity selector
	quantityLabel := widget.NewLabel("Quantity:")
	quantityEntry := widget.NewEntry()
	quantityEntry.SetText("1")

	// Add to cart button
	addButton := widget.NewButton("Add to Cart", func() {
		quantityText := quantityEntry.Text
		quantity, err := strconv.Atoi(quantityText)
		if err != nil || quantity <= 0 {
			dialog.ShowError(fmt.Errorf("please enter a valid quantity"), mainWindow)
			return
		}

		if quantity > productList.Products[index].Stock {
			dialog.ShowError(fmt.Errorf("not enough stock available"), mainWindow)
			return
		}

		// Add to cart
		addToCart(productList.Products[index], quantity)

		// Update stock
		productList.Products[index].Stock -= quantity
		stockLabel.SetText(fmt.Sprintf("In Stock: %d", productList.Products[index].Stock))

		// Reset quantity to 1
		quantityEntry.SetText("1")

		dialog.ShowInformation("Success", fmt.Sprintf("Added %d %s to cart", quantity, product.Name), mainWindow)
	})

	// Layout
	quantityContainer := container.NewHBox(quantityLabel, quantityEntry)
	detailsContainer := container.NewVBox(nameLabel, descLabel, priceLabel, stockLabel, categoryLabel, quantityContainer, addButton)

	// Card with border
	card := container.NewHBox(
		detailsContainer,
	)

	return container.NewBorder(
		nil, nil, nil, nil,
		container.NewPadded(card),
	)
}

// Add product to cart
func addToCart(product Product, quantity int) {
	cartProduct := product
	cartProduct.Quantity = quantity

	// Check if product already in cart
	found := false
	for i, item := range cart.Items {
		if item.ID == product.ID {
			cart.Items[i].Quantity += quantity
			found = true
			break
		}
	}

	if !found {
		cart.Items = append(cart.Items, cartProduct)
	}

	// Update cart totals
	updateCartTotals()

	// Save the updated stock data to JSON
	_ = saveProductsToJSON("data.json")

	// Update cart display if global references are set
	if globalCartContent != nil && globalTotalLabel != nil {
		updateCartDisplay(globalCartContent, globalTotalLabel)
	}
}

// Update cart totals
func updateCartTotals() {
	cart.TotalItems = 0
	cart.TotalPrice = 0
	for _, item := range cart.Items {
		cart.TotalItems += item.Quantity
		cart.TotalPrice += item.Price * float64(item.Quantity)
	}

	// Update item count label if available
	if globalItemCountLabel != nil {
		globalItemCountLabel.SetText(fmt.Sprintf("Items in cart: %d", cart.TotalItems))
	}
}

// Create the cart tab
func createCartTab() fyne.CanvasObject {
	// Create header
	header := widget.NewLabelWithStyle("Shopping Cart", fyne.TextAlignCenter, fyne.TextStyle{Bold: true})

	// Cart content will be dynamically updated
	cartContent := container.NewVBox()

	// Total display with larger, bolder text
	totalLabel := widget.NewLabelWithStyle(fmt.Sprintf("Total: $%.2f", cart.TotalPrice),
		fyne.TextAlignLeading, fyne.TextStyle{Bold: true, Monospace: true})

	// Make cartContent and totalLabel accessible globally
	globalCartContent = cartContent
	globalTotalLabel = totalLabel

	// Checkout button
	checkoutButton := widget.NewButton("Checkout", func() {
		if len(cart.Items) == 0 {
			dialog.ShowInformation("Cart Empty", "Your cart is empty.", mainWindow)
			return
		}

		// Save updated stock to JSON file
		err := saveProductsToJSON("data.json")
		if err != nil {
			dialog.ShowError(fmt.Errorf("failed to save product data: %v", err), mainWindow)
			return
		}

		dialog.ShowInformation("Order Placed", "Thank you for your order! Stock data has been updated.", mainWindow)

		// Reset cart after checkout
		cart.Items = []Product{}
		cart.TotalItems = 0
		cart.TotalPrice = 0
		totalLabel.SetText(fmt.Sprintf("Total: $%.2f", cart.TotalPrice))

		// Update cart display
		updateCartDisplay(cartContent, totalLabel)
	})

	// Update cart function
	updateCartFn := func() {
		updateCartDisplay(cartContent, totalLabel)
	}

	// Initial display
	updateCartDisplay(cartContent, totalLabel)

	// Create the refresh button with an icon
	refreshButton := widget.NewButtonWithIcon("Refresh Cart", theme.ViewRefreshIcon(), updateCartFn)

	// Add item count label
	itemCountLabel := widget.NewLabel(fmt.Sprintf("Items in cart: %d", cart.TotalItems))
	globalItemCountLabel = itemCountLabel

	// Bottom layout with total and checkout
	bottomContainer := container.NewVBox(
		widget.NewSeparator(),
		container.NewHBox(
			totalLabel,
			layout.NewSpacer(),
			checkoutButton,
		),
	)

	// Final layout
	return container.NewBorder(
		container.NewVBox(
			header,
			container.NewHBox(refreshButton, layout.NewSpacer(), itemCountLabel),
			widget.NewSeparator(),
		),
		bottomContainer,
		nil, nil,
		container.NewScroll(cartContent),
	)
}

// Update cart display
func updateCartDisplay(cartContent *fyne.Container, totalLabel *widget.Label) {
	cartContent.RemoveAll()

	if len(cart.Items) == 0 {
		cartContent.Add(widget.NewLabel("Your cart is empty"))
	} else {
		for i := 0; i < len(cart.Items); i++ {
			// Create a copy for closure
			item := cart.Items[i]
			index := i

			nameLabel := widget.NewLabelWithStyle(item.Name, fyne.TextAlignLeading, fyne.TextStyle{Bold: true})
			quantityLabel := widget.NewLabel(fmt.Sprintf("Quantity: %d", item.Quantity))
			priceLabel := widget.NewLabel(fmt.Sprintf("Price: $%.2f", item.Price))
			subtotalLabel := widget.NewLabel(fmt.Sprintf("Subtotal: $%.2f", item.Price*float64(item.Quantity)))

			// Remove button
			removeButton := widget.NewButton("Remove", func() {
				// Return stock to inventory
				for j, p := range productList.Products {
					if p.ID == cart.Items[index].ID {
						productList.Products[j].Stock += cart.Items[index].Quantity
						break
					}
				}

				// Remove from cart
				if index < len(cart.Items) {
					cart.Items = append(cart.Items[:index], cart.Items[index+1:]...)
					updateCartTotals()
					totalLabel.SetText(fmt.Sprintf("Total: $%.2f", cart.TotalPrice))

					// Save updated stock data
					_ = saveProductsToJSON("data.json")

					updateCartDisplay(cartContent, totalLabel)
				}
			})

			// Item container with better spacing
			itemContainer := container.NewHBox(
				container.NewVBox(
					nameLabel,
					container.NewHBox(quantityLabel, layout.NewSpacer()),
					container.NewHBox(priceLabel, layout.NewSpacer()),
					container.NewHBox(subtotalLabel, layout.NewSpacer()),
				),
				layout.NewSpacer(),
				removeButton,
			)

			cartContent.Add(container.NewBorder(nil, widget.NewSeparator(), nil, nil,
				container.NewPadded(itemContainer),
			))
		}
	}

	totalLabel.SetText(fmt.Sprintf("Total: $%.2f", cart.TotalPrice))
}
