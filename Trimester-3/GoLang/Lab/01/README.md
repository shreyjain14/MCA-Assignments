## Summary

This Go program captures product details from the user, validates the inputs, and displays the processed information. It ensures robust error handling and provides a user-friendly interface for entering and managing product data.

1. **Inputs:**
   - **Product ID:** An integer value to uniquely identify the product.
   - **Product Name:** A string representing the name of the product.
   - **Product Category:** A string indicating the category of the product.
   - **Price:** A floating-point number specifying the product's price.
   - **Quantity:** An integer representing the available stock of the product.

2. **Validation:**
   - Ensures numeric values (`Product ID`, `Price`, and `Quantity`) are entered correctly.
   - Prompts the user to re-enter values if invalid input is detected.

3. **Processing Logic:**
   - Checks stock availability based on `Quantity` and displays the appropriate status.
   - Simulates processing each item for the specified quantity.
   - Categorizes the product into predefined categories (`Clothing`, `Electronics`, or an "Unknown" default).

4. **Output:**
   - Displays a summary of the entered and processed product details in a structured format.

---

## Sample Output

```plaintext
Enter Product ID: 12
Enter Product Name: asd
Enter Product Category: asd
Enter Price: 123
Enter Quantity: 21
Product is available
Processing item 1
Processing item 2
Processing item 3
Processing item 4
Processing item 5
Processing item 6
Processing item 7
Processing item 8
Processing item 9
Processing item 10
Processing item 11
Processing item 12
Processing item 13
Processing item 14
Processing item 15
Processing item 16
Processing item 17
Processing item 18
Processing item 19
Processing item 20
Processing item 21
Unknown category

Product Details:
ID: 12
Name: asd
Category: asd
Price: 123.00
Quantity: 21
```
