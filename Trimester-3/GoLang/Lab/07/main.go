package main

import (
	"log"
	"net/http"

	"github.com/gorilla/mux"
)

func main() {
	// Initialize database
	initDB()

	// Create router
	r := mux.NewRouter()

	// Serve static files
	r.PathPrefix("/static/").Handler(http.StripPrefix("/static/", http.FileServer(http.Dir("static"))))

	// Routes
	r.HandleFunc("/", homeHandler).Methods("GET")
	r.HandleFunc("/product", productHandler).Methods("GET")
	r.HandleFunc("/api/products", apiProductsHandler).Methods("GET", "POST")
	r.HandleFunc("/api/products/price/value", updatePriceByValueHandler).Methods("POST")
	r.HandleFunc("/api/products/price/reference", updatePriceByReferenceHandler).Methods("POST")
	r.HandleFunc("/api/products/stock/reduce", reduceStockHandler).Methods("POST")

	// Demonstrate pointer concepts
	DemonstratePointers()

	// Start server
	log.Println("Server starting on http://localhost:8080")
	log.Fatal(http.ListenAndServe(":8080", r))
}
