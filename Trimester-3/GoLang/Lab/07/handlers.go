package main

import (
	"encoding/json"
	"html/template"
	"net/http"
	"strconv"
)

var templates = template.Must(template.ParseGlob("templates/*.html"))

func homeHandler(w http.ResponseWriter, r *http.Request) {
	products, err := getAllProducts()
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	templates.ExecuteTemplate(w, "home.html", products)
}

func productHandler(w http.ResponseWriter, r *http.Request) {
	id, err := strconv.Atoi(r.URL.Query().Get("id"))
	if err != nil {
		http.Error(w, "Invalid product ID", http.StatusBadRequest)
		return
	}

	product, err := getProductByID(id)
	if err != nil {
		http.Error(w, "Product not found", http.StatusNotFound)
		return
	}

	templates.ExecuteTemplate(w, "product.html", product)
}

func apiProductsHandler(w http.ResponseWriter, r *http.Request) {
	switch r.Method {
	case "GET":
		products, err := getAllProducts()
		if err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}
		json.NewEncoder(w).Encode(products)

	case "POST":
		var product Product
		if err := json.NewDecoder(r.Body).Decode(&product); err != nil {
			http.Error(w, err.Error(), http.StatusBadRequest)
			return
		}

		if err := addProduct(&product); err != nil {
			http.Error(w, err.Error(), http.StatusInternalServerError)
			return
		}

		w.WriteHeader(http.StatusCreated)
		json.NewEncoder(w).Encode(product)
	}
}

// New handler for updating price by value
func updatePriceByValueHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method != "POST" {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}

	var request struct {
		ID    int     `json:"id"`
		Price float64 `json:"price"`
	}

	if err := json.NewDecoder(r.Body).Decode(&request); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	product, err := getProductByID(request.ID)
	if err != nil {
		http.Error(w, "Product not found", http.StatusNotFound)
		return
	}

	// Create a copy of the product to demonstrate call by value
	productCopy := *product
	productCopy.UpdatePriceByValue(request.Price)

	// Return both original and updated prices to show the difference
	response := struct {
		OriginalPrice float64 `json:"original_price"`
		UpdatedPrice  float64 `json:"updated_price"`
	}{
		OriginalPrice: product.Price,
		UpdatedPrice:  productCopy.Price,
	}

	json.NewEncoder(w).Encode(response)
}

// New handler for updating price by reference
func updatePriceByReferenceHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method != "POST" {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}

	var request struct {
		ID    int     `json:"id"`
		Price float64 `json:"price"`
	}

	if err := json.NewDecoder(r.Body).Decode(&request); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	product, err := getProductByID(request.ID)
	if err != nil {
		http.Error(w, "Product not found", http.StatusNotFound)
		return
	}

	// Update price using call by reference
	product.UpdatePriceByReference(request.Price)

	// Update the price in the database
	if err := updateProductPrice(product.ID, product.Price); err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	json.NewEncoder(w).Encode(product)
}

// New handler for reducing stock
func reduceStockHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method != "POST" {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}

	var request struct {
		ID int `json:"id"`
	}

	if err := json.NewDecoder(r.Body).Decode(&request); err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}

	product, err := getProductByID(request.ID)
	if err != nil {
		http.Error(w, "Product not found", http.StatusNotFound)
		return
	}

	if product.Stock <= 0 {
		http.Error(w, "Product is out of stock", http.StatusBadRequest)
		return
	}

	// Reduce stock by 1
	if err := updateProductStock(product.ID, product.Stock-1); err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	// Get updated product
	product, err = getProductByID(request.ID)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	json.NewEncoder(w).Encode(product)
}
