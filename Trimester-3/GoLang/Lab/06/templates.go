package main

const shopTemplate = `
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>E-commerce App</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            color: #333;
            max-width: 1200px;
            margin: 0 auto;
        }
        header {
            background-color: #f4f4f4;
            padding: 1rem;
            margin-bottom: 1rem;
            text-align: center;
        }
        h1, h2 {
            color: #333;
        }
        .container {
            display: flex;
            gap: 2rem;
        }
        .products {
            flex: 2;
        }
        .cart {
            flex: 1;
            background-color: #f9f9f9;
            padding: 1rem;
            border-radius: 5px;
        }
        .product-card {
            border: 1px solid #ddd;
            padding: 1rem;
            margin-bottom: 1rem;
            border-radius: 5px;
        }
        .product-card h3 {
            margin-top: 0;
        }
        .btn {
            display: inline-block;
            background: #333;
            color: white;
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 3px;
            cursor: pointer;
            text-decoration: none;
            font-size: 1rem;
            margin-right: 0.5rem;
        }
        .btn:hover {
            opacity: 0.9;
        }
        .btn-primary {
            background: #007bff;
        }
        .btn-danger {
            background: #dc3545;
        }
        input[type="number"] {
            width: 50px;
            padding: 0.3rem;
        }
        .cart-item {
            border-bottom: 1px solid #ddd;
            padding: 0.5rem 0;
            margin-bottom: 0.5rem;
        }
        .alert {
            padding: 0.75rem 1.25rem;
            margin-bottom: 1rem;
            border: 1px solid transparent;
            border-radius: 0.25rem;
        }
        .alert-success {
            color: #155724;
            background-color: #d4edda;
            border-color: #c3e6cb;
        }
        .alert-danger {
            color: #721c24;
            background-color: #f8d7da;
            border-color: #f5c6cb;
        }
        #message {
            display: none;
        }
    </style>
</head>
<body>
    <header>
        <h1>E-commerce App</h1>
    </header>
    
    <div id="message" class="alert"></div>
    
    <div class="container">
        <div class="products">
            <h2>Products</h2>
            <div id="product-list"></div>
        </div>
        
        <div class="cart">
            <h2>Your Cart</h2>
            <div id="cart-items"></div>
            <div id="cart-total"></div>
            <div id="checkout-btn" style="margin-top: 1rem; text-align: center;"></div>
        </div>
    </div>

    <script>
        // Fetch and display products
        async function fetchProducts() {
            try {
                const response = await fetch('/api/products');
                const products = await response.json();
                displayProducts(products);
            } catch (error) {
                showMessage('Error loading products', false);
            }
        }
        
        // Fetch and display cart
        async function fetchCart() {
            try {
                const response = await fetch('/api/cart');
                const cart = await response.json();
                displayCart(cart);
            } catch (error) {
                showMessage('Error loading cart', false);
            }
        }
        
        // Display products on the page
        function displayProducts(products) {
            const productList = document.getElementById('product-list');
            productList.innerHTML = '';
            
            products.forEach(product => {
                const productCard = document.createElement('div');
                productCard.className = 'product-card';
                
                productCard.innerHTML = `
                    <h3>${product.Name} - $${product.Price.toFixed(2)}</h3>
                    <p><strong>Category:</strong> ${product.Category}</p>
                    <p>${product.Description}</p>
                    <form id="add-form-${product.ID}">
                        <input type="number" name="quantity" value="1" min="1">
                        <button type="submit" class="btn btn-primary">Add to Cart</button>
                    </form>
                `;
                
                productList.appendChild(productCard);
                
                // Add event listener to form
                document.getElementById(`add-form-${product.ID}`).addEventListener('submit', function(e) {
                    e.preventDefault();
                    const quantity = this.querySelector('input[name="quantity"]').value;
                    addToCart(product.ID, quantity);
                });
            });
        }
        
        // Display cart items
        function displayCart(cart) {
            const cartItems = document.getElementById('cart-items');
            const cartTotal = document.getElementById('cart-total');
            const checkoutBtn = document.getElementById('checkout-btn');
            
            if (cart.items.length === 0) {
                cartItems.innerHTML = '<p>Your cart is empty</p>';
                cartTotal.textContent = '';
                checkoutBtn.innerHTML = '';
                return;
            }
            
            // Display items
            cartItems.innerHTML = '';
            cart.items.forEach(item => {
                const itemElement = document.createElement('div');
                itemElement.className = 'cart-item';
                
                itemElement.innerHTML = `
                    <div>${item.name} - $${item.price.toFixed(2)} x 
                        <input type="number" value="${item.quantity}" min="1" 
                               onchange="updateCartItem(${item.id}, this.value)">
                        = $${item.totalPrice.toFixed(2)}
                    </div>
                    <button class="btn btn-danger btn-sm" onclick="removeFromCart(${item.id})">Remove</button>
                `;
                
                cartItems.appendChild(itemElement);
            });
            
            // Display total
            cartTotal.innerHTML = `<h3>Total: $${cart.totalPrice.toFixed(2)}</h3>`;
            
            // Display checkout button
            checkoutBtn.innerHTML = '<button class="btn btn-primary" onclick="checkout()">Checkout</button>';
        }
        
        // Add product to cart
        async function addToCart(productId, quantity) {
            try {
                const formData = new FormData();
                formData.append('product_id', productId);
                formData.append('quantity', quantity);
                
                const response = await fetch('/api/cart/add', {
                    method: 'POST',
                    body: formData
                });
                
                if (!response.ok) {
                    const error = await response.text();
                    throw new Error(error);
                }
                
                const cart = await response.json();
                displayCart(cart);
                showMessage('Product added to cart!', true);
            } catch (error) {
                showMessage(error.message, false);
            }
        }
        
        // Update cart item quantity
        async function updateCartItem(productId, quantity) {
            try {
                const formData = new FormData();
                formData.append('product_id', productId);
                formData.append('quantity', quantity);
                
                const response = await fetch('/api/cart/update', {
                    method: 'POST',
                    body: formData
                });
                
                if (!response.ok) {
                    const error = await response.text();
                    throw new Error(error);
                }
                
                const cart = await response.json();
                displayCart(cart);
                showMessage('Cart updated!', true);
            } catch (error) {
                showMessage(error.message, false);
                fetchCart(); // Refresh cart on error
            }
        }
        
        // Remove item from cart
        async function removeFromCart(productId) {
            try {
                const formData = new FormData();
                formData.append('product_id', productId);
                
                const response = await fetch('/api/cart/remove', {
                    method: 'POST',
                    body: formData
                });
                
                if (!response.ok) {
                    const error = await response.text();