package main

import (
	"fmt"
	"html/template"
	"net/http"
	"strconv"
)

type Product struct {
	ID       int
	Name     string
	Price    float64
	Category string
}

var categories = []string{"Electronics", "Books", "Clothing", "Home"}

var products = map[string][]Product{
	"Electronics": {},
	"Books":       {},
	"Clothing":    {},
	"Home":        {},
}

var templates = template.Must(template.ParseFiles("index.html"))

func main() {
	http.HandleFunc("/", homeHandler)
	http.HandleFunc("/add", addProductHandler)
	http.ListenAndServe(":8080", nil)
}

type TemplateData struct {
	Categories []string
	Products   map[string][]Product
}

func homeHandler(w http.ResponseWriter, r *http.Request) {
	data := TemplateData{
		Categories: categories,
		Products:   products,
	}
	templates.ExecuteTemplate(w, "index.html", data)
}

func addProductHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method == http.MethodPost {
		category := r.FormValue("category")
		name := r.FormValue("name")
		price, _ := strconv.ParseFloat(r.FormValue("price"), 64)

		newProduct := Product{
			Name:     name,
			Price:    price,
			Category: category,
		}

		products[category] = append(products[category], newProduct)
		http.Redirect(w, r, "/", http.StatusSeeOther)
	}
}

func parsePrice(price string) float64 {
	var p float64
	fmt.Sscanf(price, "%f", &p)
	return p
}
