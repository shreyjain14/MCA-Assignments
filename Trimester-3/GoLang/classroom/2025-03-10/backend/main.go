package main

import (
	"log"
	"net/http"
	"os"
	"os/signal"
	"syscall"

	"github.com/gorilla/mux"

	"banking/db"
	"banking/handlers"
	"banking/middleware"
)

func main() {
	// Initialize database
	if err := db.InitDB(); err != nil {
		log.Fatalf("Failed to initialize database: %v", err)
	}

	// Create router
	router := mux.NewRouter()

	// Apply CORS middleware to all routes
	router.Use(middleware.CORSMiddleware)

	// API routes
	router.HandleFunc("/api/users", handlers.CreateUserHandler).Methods("POST")
	router.HandleFunc("/api/accounts", handlers.CreateAccountHandler).Methods("POST")
	router.HandleFunc("/api/users/{user_id}/accounts", handlers.GetAccountsHandler).Methods("GET")
	router.HandleFunc("/api/accounts/{account_id}", handlers.GetAccountHandler).Methods("GET")
	router.HandleFunc("/api/accounts/{account_id}/deposit", handlers.DepositHandler).Methods("POST")
	router.HandleFunc("/api/accounts/{account_id}/withdraw", handlers.WithdrawHandler).Methods("POST")
	router.HandleFunc("/api/accounts/{account_id}/transactions", handlers.GetTransactionHistoryHandler).Methods("GET")

	// Documentation routes
	router.PathPrefix("/docs/").Handler(http.StripPrefix("/docs/", http.FileServer(http.Dir("./docs"))))
	router.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		http.ServeFile(w, r, "./docs/redoc.html")
	})

	// Create HTTP server
	server := &http.Server{
		Addr:    ":8080",
		Handler: router,
	}

	// Start server in a goroutine
	go func() {
		log.Println("Starting server on :8080")
		log.Println("API Documentation available at http://localhost:8080/")
		if err := server.ListenAndServe(); err != nil && err != http.ErrServerClosed {
			log.Fatalf("Server error: %v", err)
		}
	}()

	// Graceful shutdown
	quit := make(chan os.Signal, 1)
	signal.Notify(quit, syscall.SIGINT, syscall.SIGTERM)
	<-quit

	log.Println("Shutting down server...")

	// Close DB connection
	if db.DB != nil {
		log.Println("Closing database connection...")
		db.DB.Close()
	}

	log.Println("Server stopped")
}
