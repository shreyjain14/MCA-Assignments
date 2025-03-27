package main

import (
	"os"
	"testing"
)

// Setup test data
func setupTestData() {
	// Create test products
	productList = Products{
		Products: []Product{
			{
				ID:          1,
				Name:        "Test Product 1",
				Description: "Test Description 1",
				Price:       10.99,
				Stock:       10,
				Category:    "Test Category",
			},
			{
				ID:          2,
				Name:        "Test Product 2",
				Description: "Test Description 2",
				Price:       20.99,
				Stock:       20,
				Category:    "Test Category",
			},
		},
	}

	// Initialize empty cart
	cart = Cart{
		Items:      []Product{},
		TotalItems: 0,
		TotalPrice: 0,
	}
}

// Test loading products from JSON
func TestLoadProductsFromJSON(t *testing.T) {
	// Create a temporary test JSON file
	tempFile, err := os.CreateTemp("", "test-data-*.json")
	if err != nil {
		t.Fatalf("Failed to create temporary file: %v", err)
	}
	defer os.Remove(tempFile.Name())

	// Write test data to file
	testData := `{
		"products": [
			{
				"id": 1,
				"name": "Test Product",
				"description": "This is a test product",
				"price": 9.99,
				"stock": 5,
				"category": "Test"
			}
		]
	}`

	if _, err := tempFile.Write([]byte(testData)); err != nil {
		t.Fatalf("Failed to write test data: %v", err)
	}
	tempFile.Close()

	// Test loading products
	err = loadProductsFromJSON(tempFile.Name())
	if err != nil {
		t.Errorf("loadProductsFromJSON failed: %v", err)
	}

	// Verify data
	if len(productList.Products) != 1 {
		t.Errorf("Expected 1 product, got %d", len(productList.Products))
	}

	p := productList.Products[0]
	if p.ID != 1 || p.Name != "Test Product" || p.Price != 9.99 || p.Stock != 5 {
		t.Errorf("Product data incorrect: %+v", p)
	}
}

// Test saving products to JSON
func TestSaveProductsToJSON(t *testing.T) {
	// Setup test data
	setupTestData()

	// Create a temporary file
	tempFile, err := os.CreateTemp("", "test-save-*.json")
	if err != nil {
		t.Fatalf("Failed to create temporary file: %v", err)
	}
	defer os.Remove(tempFile.Name())
	tempFile.Close()

	// Save products
	err = saveProductsToJSON(tempFile.Name())
	if err != nil {
		t.Errorf("saveProductsToJSON failed: %v", err)
	}

	// Verify file exists and has content
	info, err := os.Stat(tempFile.Name())
	if err != nil {
		t.Errorf("Failed to stat file: %v", err)
	}

	if info.Size() == 0 {
		t.Error("Saved file is empty")
	}

	// Try to load the file back to verify content
	testProducts := Products{}
	productList = testProducts

	err = loadProductsFromJSON(tempFile.Name())
	if err != nil {
		t.Errorf("Failed to load saved file: %v", err)
	}

	if len(productList.Products) != 2 {
		t.Errorf("Expected 2 products after loading saved file, got %d", len(productList.Products))
	}
}

// Test adding products to cart
func TestAddToCart(t *testing.T) {
	// Setup test data
	setupTestData()

	// Add product to cart
	addToCart(productList.Products[0], 2)

	// Check cart data
	if len(cart.Items) != 1 {
		t.Errorf("Expected 1 item in cart, got %d", len(cart.Items))
	}

	if cart.TotalItems != 2 {
		t.Errorf("Expected total items to be 2, got %d", cart.TotalItems)
	}

	expectedTotal := 2 * 10.99
	if cart.TotalPrice != expectedTotal {
		t.Errorf("Expected total price to be %.2f, got %.2f", expectedTotal, cart.TotalPrice)
	}

	// Add another product
	addToCart(productList.Products[1], 1)

	if len(cart.Items) != 2 {
		t.Errorf("Expected 2 items in cart, got %d", len(cart.Items))
	}

	if cart.TotalItems != 3 {
		t.Errorf("Expected total items to be 3, got %d", cart.TotalItems)
	}

	expectedTotal = (2 * 10.99) + (1 * 20.99)
	if cart.TotalPrice != expectedTotal {
		t.Errorf("Expected total price to be %.2f, got %.2f", expectedTotal, cart.TotalPrice)
	}

	// Add the same product again (should update quantity)
	addToCart(productList.Products[0], 1)

	if len(cart.Items) != 2 {
		t.Errorf("Expected still 2 items in cart, got %d", len(cart.Items))
	}

	if cart.Items[0].Quantity != 3 {
		t.Errorf("Expected first product quantity to be 3, got %d", cart.Items[0].Quantity)
	}

	if cart.TotalItems != 4 {
		t.Errorf("Expected total items to be 4, got %d", cart.TotalItems)
	}
}

// Test updating cart totals
func TestUpdateCartTotals(t *testing.T) {
	// Setup test data with pre-populated cart
	setupTestData()

	// Add items to cart manually
	cart.Items = []Product{
		{
			ID:       1,
			Name:     "Test Product 1",
			Price:    10.00,
			Quantity: 2,
		},
		{
			ID:       2,
			Name:     "Test Product 2",
			Price:    15.50,
			Quantity: 3,
		},
	}

	// Calculate totals
	updateCartTotals()

	// Check results
	expectedItems := 5
	if cart.TotalItems != expectedItems {
		t.Errorf("Expected total items to be %d, got %d", expectedItems, cart.TotalItems)
	}

	expectedPrice := (2 * 10.00) + (3 * 15.50)
	if cart.TotalPrice != expectedPrice {
		t.Errorf("Expected total price to be %.2f, got %.2f", expectedPrice, cart.TotalPrice)
	}
}
