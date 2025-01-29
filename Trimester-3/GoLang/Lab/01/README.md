## Summary

This Go program captures product details from the user, validates the inputs, and displays the processed information. It ensures robust error handling and provides a user-friendly interface for entering and managing product data. WHile the data is stored in a list and can be viewed later.

---

## Sample Output

```plaintext
=== Product Management System ===
1. Add New Product
2. View All Products
3. Exit
Enter your choice: 1

=== Add New Product ===
Enter Product ID: d
Please enter a valid integer for Product ID
Enter Product ID: 1
Enter Product Name: abc
Enter Category: abc
Enter Price: s
Please enter a valid price
Enter Price: 12
Enter Quantity: 20

Product added successfully!

=== Product Management System ===
1. Add New Product
2. View All Products
3. Exit
Enter your choice: 2

=== Product List ===
ID      Name            Category        Price   Quantity
------------------------------------------------
1       abc             abc             12.00   20

=== Product Management System ===
1. Add New Product
2. View All Products
3. Exit
Enter your choice: 1

=== Add New Product ===
Enter Product ID: 3
Enter Product Name: asd
Enter Category: asd
Enter Price: 32
Enter Quantity: 1

Product added successfully!

=== Product Management System ===
1. Add New Product
2. View All Products
3. Exit
Enter your choice: 2

=== Product List ===
ID      Name            Category        Price   Quantity
------------------------------------------------
1       abc             abc             12.00   20
3       asd             asd             32.00   1

=== Product Management System ===
1. Add New Product
2. View All Products
3. Exit
Enter your choice: 3
Goodbye!
```
