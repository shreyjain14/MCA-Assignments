package main

import (
	"encoding/json"
	"html/template"
	"log"
	"net/http"
	"sync"
	"time"
)

// Product represents an e-commerce product
type Product struct {
	ID          int     `json:"id"`
	Name        string  `json:"name"`
	Price       float64 `json:"price"`
	Stock       int     `json:"stock"`
	Description string  `json:"description"`
}

// Order represents a customer order
type Order struct {
	ID        int       `json:"id"`
	ProductID int       `json:"product_id"`
	Quantity  int       `json:"quantity"`
	Status    string    `json:"status"`
	CreatedAt time.Time `json:"created_at"`
}

// Store represents our e-commerce store
type Store struct {
	products []Product
	orders   []Order
	mu       sync.RWMutex
	orderID  int // Counter for generating unique order IDs
}

var store = &Store{
	products: []Product{
		{ID: 1, Name: "Laptop", Price: 999.99, Stock: 10, Description: "High-performance laptop"},
		{ID: 2, Name: "Smartphone", Price: 499.99, Stock: 20, Description: "Latest smartphone"},
		{ID: 3, Name: "Headphones", Price: 99.99, Stock: 30, Description: "Wireless headphones"},
	},
	orderID: 1, // Initialize order ID counter
}

func main() {
	// Create templates directory
	http.Handle("/static/", http.StripPrefix("/static/", http.FileServer(http.Dir("static"))))

	// Routes
	http.HandleFunc("/", handleHome)
	http.HandleFunc("/api/products", handleProducts)
	http.HandleFunc("/api/orders", handleOrders)

	// Start order processing goroutine
	go processOrders()

	log.Println("Server starting on :8080...")
	log.Fatal(http.ListenAndServe(":8080", nil))
}

func handleHome(w http.ResponseWriter, r *http.Request) {
	tmpl := template.Must(template.ParseFiles("templates/index.html"))
	tmpl.Execute(w, nil)
}

func handleProducts(w http.ResponseWriter, r *http.Request) {
	store.mu.RLock()
	defer store.mu.RUnlock()

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(store.products)
}

func handleOrders(w http.ResponseWriter, r *http.Request) {
	if r.Method == "POST" {
		var order Order
		if err := json.NewDecoder(r.Body).Decode(&order); err != nil {
			http.Error(w, err.Error(), http.StatusBadRequest)
			return
		}

		store.mu.Lock()
		order.ID = store.orderID // Assign unique order ID
		store.orderID++          // Increment the counter
		order.CreatedAt = time.Now()
		order.Status = "pending"
		store.orders = append(store.orders, order)
		store.mu.Unlock()

		w.WriteHeader(http.StatusCreated)
		json.NewEncoder(w).Encode(order)
		return
	}

	store.mu.RLock()
	defer store.mu.RUnlock()

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(store.orders)
}

func processOrders() {
	orderChan := make(chan Order, 100)

	// Start multiple order processing goroutines
	for i := 0; i < 3; i++ {
		go func(workerID int) {
			for order := range orderChan {
				// Simulate order processing
				time.Sleep(time.Second * 2)

				store.mu.Lock()
				for i := range store.orders {
					if store.orders[i].ID == order.ID {
						store.orders[i].Status = "completed"
						break
					}
				}
				store.mu.Unlock()

				log.Printf("Worker %d processed order %d\n", workerID, order.ID)
			}
		}(i)
	}

	// Monitor for new orders
	for {
		store.mu.RLock()
		for _, order := range store.orders {
			if order.Status == "pending" {
				orderChan <- order
			}
		}
		store.mu.RUnlock()
		time.Sleep(time.Second)
	}
}
