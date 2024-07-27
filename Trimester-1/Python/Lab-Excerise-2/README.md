# Lab Exercise 2

**Shrey Jain
2447249
1MCA-B**

### 1. Write a Python program to create a module that performs basic list opera tions. Follow the instructions given below to write the program: 

(a) Create a module, named ‘module_ListFunction’ which has the following functions: 
- A function that provides the maximum value in the defined list. 
- A function that provides the minimum value. 
- A function that provides the sum of all elements in a list. 
- A function that provides the average of the list. 
- A function that finds the median of a list. 

(b) Create another script named ‘main_ListOpeartions.py’ and Imports the ‘module_ListFunction’ to it. 

(c) Demonstrate the execution of each function with suitable examples. 

OUTPUT:

```
Enter the elements of the list (separated by spaces): 1 51 54 21 2 45 4854 15 12 21 54 53 9 97 7 445 65 21
1. Calculate sum    
2. Find maximum     
3. Find minimum     
4. Calculate average
5. Calculate median 
0. Exit
Enter your choice: 1
Sum: 5827
1. Calculate sum    
2. Find maximum     
3. Find minimum     
4. Calculate average
5. Calculate median 
0. Exit
Enter your choice: 2
Maximum: 4854       
1. Calculate sum    
2. Find maximum     
3. Find minimum     
4. Calculate average
5. Calculate median 
0. Exit
Enter your choice: 3
Minimum: 1
1. Calculate sum
2. Find maximum
3. Find minimum
4. Calculate average
5. Calculate median
0. Exit
Enter your choice: 4
Average: 323.72222222222223
1. Calculate sum
2. Find maximum
3. Find minimum
4. Calculate average
5. Calculate median
0. Exit
Enter your choice: 5
Median: 33.0
1. Calculate sum
2. Find maximum
3. Find minimum
4. Calculate average
5. Calculate median
0. Exit
Enter your choice: 6
Invalid choice. Please try again.
1. Calculate sum
2. Find maximum
3. Find minimum
4. Calculate average
5. Calculate median
0. Exit
Enter your choice: 0
Exiting...
```

### 2. Write a Python program to create a module that performs various set operations.
1. Write a function that adds an element to a set. 
2. Write a function that removes an element from a set. 
3. Write a function that delivers the union and intersection of two sets.
4. Write a function that returns the difference of two sets say, S1 and S2. 
5. For any arbitrary sets S1 and S2, write a function that checks if set S1 is a subset of set S2. 
6. Write a function that finds the length of a given set without using the ‘len()’ function. 
7. Write a function to get all unique subsets of a given set. 

Implement this module and demonstrate it by using adequate examples. 

OUTPUT:

```
            --------------------     
            MENU

            1. Change set1
            2. Change set2
            3. Add element
            4. Remove element        
            5. Union and intersection
            6. Difference
            7. Check subset
            8. Symmetric difference  
            9. Power set
            10. Unique subsets       
            11. Quit
            --------------------     


Enter option: 1
Enter the length of the first set: 0

            --------------------
            MENU

            1. Change set1      
            2. Change set2
            3. Add element
            4. Remove element
            5. Union and intersection
            6. Difference
            7. Check subset
            8. Symmetric difference
            9. Power set
            10. Unique subsets
            11. Quit
            --------------------


Enter option: 2
Enter the length of the second set: 6
Enter element 1: 1
Enter element 2: 2
Enter element 3: 3
Enter element 4: 4
Enter element 5: 5
Enter element 6: 6

            --------------------
            MENU

            1. Change set1
            2. Change set2
            3. Add element
            4. Remove element
            5. Union and intersection
            6. Difference
            7. Check subset
            8. Symmetric difference
            9. Power set
            10. Unique subsets
            11. Quit
            --------------------


Enter option: 3
Enter the element to add: 1
add element:  {1}

            --------------------
            MENU

            1. Change set1
            2. Change set2
            3. Add element
            4. Remove element
            5. Union and intersection
            6. Difference
            7. Check subset
            8. Symmetric difference
            9. Power set
            10. Unique subsets
            11. Quit
            --------------------


Enter option: 4
Enter the element to remove: 1
remove element:  set()

            --------------------
            MENU

            1. Change set1
            2. Change set2
            3. Add element
            4. Remove element
            5. Union and intersection
            6. Difference
            7. Check subset
            8. Symmetric difference
            9. Power set
            10. Unique subsets
            11. Quit
            --------------------


Enter option: 5
union and intersection:  ({1, 2, 3, 4, 5, 6}, set())

            --------------------
            MENU

            1. Change set1
            2. Change set2
            3. Add element
            4. Remove element
            5. Union and intersection
            6. Difference
            7. Check subset
            8. Symmetric difference
            9. Power set
            10. Unique subsets
            11. Quit
            --------------------


Enter option: 6
difference:  set()

            --------------------
            MENU

            1. Change set1
            2. Change set2
            3. Add element
            4. Remove element
            5. Union and intersection
            6. Difference
            7. Check subset
            8. Symmetric difference
            9. Power set
            10. Unique subsets
            11. Quit
            --------------------


Enter option: 1
Enter the length of the first set: 3
Enter element 1: 1
Enter element 2: 2
Enter element 3: 3

            --------------------
            MENU

            1. Change set1
            2. Change set2
            3. Add element
            4. Remove element
            5. Union and intersection
            6. Difference
            7. Check subset
            8. Symmetric difference
            9. Power set
            10. Unique subsets
            11. Quit
            --------------------


Enter option: 5
union and intersection:  ({1, 2, 3, 4, 5, 6}, {1, 2, 3})

            --------------------
            MENU

            1. Change set1
            2. Change set2
            3. Add element
            4. Remove element
            5. Union and intersection
            6. Difference
            7. Check subset
            8. Symmetric difference
            9. Power set
            10. Unique subsets
            11. Quit
            --------------------


Enter option: 6
difference:  set()

            --------------------
            MENU

            1. Change set1
            2. Change set2
            3. Add element
            4. Remove element
            5. Union and intersection
            6. Difference
            7. Check subset
            8. Symmetric difference
            9. Power set
            10. Unique subsets
            11. Quit
            --------------------


Enter option: 7 
check subset:  True

            --------------------
            MENU

            1. Change set1
            2. Change set2
            3. Add element
            4. Remove element
            5. Union and intersection
            6. Difference
            7. Check subset
            8. Symmetric difference
            9. Power set
            10. Unique subsets
            11. Quit
            --------------------


Enter option: 8
symmetric differnce:  {4, 5, 6}

            --------------------
            MENU

            1. Change set1
            2. Change set2
            3. Add element
            4. Remove element
            5. Union and intersection
            6. Difference
            7. Check subset
            8. Symmetric difference
            9. Power set
            10. Unique subsets
            11. Quit
            --------------------


Enter option: 9
powerset [{1, 2, 3}]

            --------------------
            MENU

            1. Change set1
            2. Change set2
            3. Add element
            4. Remove element
            5. Union and intersection
            6. Difference
            7. Check subset
            8. Symmetric difference
            9. Power set
            10. Unique subsets
            11. Quit
            --------------------


Enter option: 10
unique subset [set(), {1}, {2}, {1, 2}, {3}, {1, 3}, {2, 3}, {1, 2, 3}]

            --------------------
            MENU

            1. Change set1
            2. Change set2
            3. Add element
            4. Remove element
            5. Union and intersection
            6. Difference
            7. Check subset
            8. Symmetric difference
            9. Power set
            10. Unique subsets
            11. Quit
            --------------------


Enter option: 11
```

### 3. Write a program to create functions that can accept multiple dictionaries as arguments using ‘*args’, and perform various operations on them.

1. Write a function, say, ‘merging_Dict(*args)’ that takes multiple dictio naries and merge them. 
2. Write a function which can find common keys in multiple dictionaries. 
3. Create a function that inverts a dictionary by swapping its keys and values. If multiple keys have the same value in the original dictionary, they should be grouped in a list as the value in the inverted dictionary. Implement this program and demonstrate it by using adequate examples. 

OUTPUT:

```
            --------------------
            MENU

            1. Merge dictionaries
            2. Find common keys
            3. Invert dictionary (a)      
            4. Find common Key value pairs
            5. Exit
            --------------------

Enter your choice: 1
merged dicts:  {'a': 1, 'g': 1, 'b': 2, 'c': 4}

            --------------------
            MENU

            1. Merge dictionaries
            2. Find common keys
            3. Invert dictionary (a)
            4. Find common Key value pairs     
            5. Exit
            --------------------

Enter your choice: 2
common keys:  {'b'}

            --------------------
            MENU

            1. Merge dictionaries
            2. Find common keys
            3. Invert dictionary (a)
            4. Find common Key value pairs
            5. Exit
            --------------------

Enter your choice: 2
common keys:  {'b'}

            --------------------
            MENU

            1. Merge dictionaries
            2. Find common keys
            3. Invert dictionary (a)
            4. Find common Key value pairs
            5. Exit
            --------------------

Enter your choice: 4
common key value pairs:  {('b', 2)}

            --------------------
            MENU

            1. Merge dictionaries
            2. Find common keys
            3. Invert dictionary (a)
            4. Find common Key value pairs
            5. Exit
            --------------------

Enter your choice: 5
```

### 4. Write a Python program to efficiently handle and manage the books in a library and this program should have functions to add a book, remove a book, and get the details of a book. Each book is represented with the following details as title, author, publisher, volume, year of publication, and ISBN (International Standard Book Number). 
Follow the instructions given below to write the program: 
1. Create a module named ‘LibraryBooks.py’ to manage books. 
2. Collect the information from web resources for 25 newly published books on subjects, namely Operating Systems, Data structures, and Machine Learning using Python, between the years 2020 and 2024 [ The collected information may be stored in a Dictionary as key: value pairs]. 
3. Create the functions for adding a book, removing a book, and getting the book details, and then place those functions in the created module, ‘LibraryBooks.py’.
4. Create another module named. ‘mainLibrayManagement.py’ to use these functions. 

OUTPUT:

```
            --------------------      
            MENU

            1. Add book
            2. Remove book
            3. Get book details       
            4. Search books
            5. List all books
            6. Update book details    
            7. Check book availability
            8. Exit
            --------------------      

Enter your choice: 1
Enter the title of the book: abc
Enter the author of the book: abc
Enter the publisher of the book: abc
Enter the volume of the book: abc
Enter the year of publication: 2003
Enter the ISBN of the book: 0
Book added successfully.        

            --------------------
            MENU

            1. Add book
            2. Remove book
            3. Get book details
            4. Search books
            5. List all books
            6. Update book details
            7. Check book availability
            8. Exit
            --------------------

Enter your choice: 1
Enter the title of the book: abb
Enter the author of the book: abb
Enter the publisher of the book: abb
Enter the volume of the book: abb
Enter the year of publication: 2096
Enter the ISBN of the book: 1
Book added successfully.

            --------------------
            MENU

            1. Add book
            2. Remove book
            3. Get book details
            4. Search books
            5. List all books
            6. Update book details
            7. Check book availability
            8. Exit
            --------------------

Enter your choice: 3
Enter the ISBN of the book to get details: 0
Title: abc
Author: abc
Publisher: abc
Volume: abc
Year: 2003
ISBN: 0

            --------------------
            MENU

            1. Add book
            2. Remove book
            3. Get book details
            4. Search books
            5. List all books
            6. Update book details
            7. Check book availability
            8. Exit
            --------------------

Enter your choice: 4
Enter the keyword to search books: ab
Found books:
0 abc
1 abb

            --------------------
            MENU

            1. Add book
            2. Remove book
            3. Get book details
            4. Search books
            5. List all books
            6. Update book details
            7. Check book availability
            8. Exit
            --------------------

Enter your choice: 4
Enter the keyword to search books: c
Found books:
0 abc

            --------------------
            MENU

            1. Add book
            2. Remove book
            3. Get book details
            4. Search books
            5. List all books
            6. Update book details
            7. Check book availability
            8. Exit
            --------------------

Enter your choice: 5
All books:
Title: abc
Author: abc
Publisher: abc
Volume: abc
Year: 2003
ISBN: 0

Title: abb
Author: abb
Publisher: abb
Volume: abb
Year: 2096
ISBN: 1


            --------------------
            MENU

            1. Add book
            2. Remove book
            3. Get book details
            4. Search books
            5. List all books
            6. Update book details
            7. Check book availability
            8. Exit
            --------------------

Enter your choice: 6
Enter the ISBN of the book to update details: 1
Enter the new title (leave blank to keep existing): 
Enter the new author (leave blank to keep existing): acc
Enter the new publisher (leave blank to keep existing): 
Enter the new volume (leave blank to keep existing): 
Enter the new year (leave blank to keep existing): 2001
Book details updated successfully.

            --------------------
            MENU

            1. Add book
            2. Remove book
            3. Get book details
            4. Search books
            5. List all books
            6. Update book details
            7. Check book availability
            8. Exit
            --------------------

Enter your choice: 3
Enter the ISBN of the book to get details: 1
Title: abb
Author: acc
Publisher: abb
Volume: abb
Year: 2001
ISBN: 1

            --------------------
            MENU

            1. Add book
            2. Remove book
            3. Get book details
            4. Search books
            5. List all books
            6. Update book details
            7. Check book availability
            8. Exit
            --------------------

Enter your choice: 7
Enter the ISBN of the book to check availability: 3
Book is not available.

            --------------------
            MENU

            1. Add book
            2. Remove book
            3. Get book details
            4. Search books
            5. List all books
            6. Update book details
            7. Check book availability
            8. Exit
            --------------------

Enter your choice: 7
Enter the ISBN of the book to check availability: 1
Book is available.

            --------------------
            MENU

            1. Add book
            2. Remove book
            3. Get book details
            4. Search books
            5. List all books
            6. Update book details
            7. Check book availability
            8. Exit
            --------------------

Enter your choice: 6
Enter the ISBN of the book to update details: 9
Book is not available to change.

            --------------------
            MENU

            1. Add book
            2. Remove book
            3. Get book details
            4. Search books
            5. List all books
            6. Update book details
            7. Check book availability
            8. Exit
            --------------------

Enter your choice: 2
Enter the ISBN of the book to remove: 1
Book removed successfully.

            --------------------
            MENU

            1. Add book
            2. Remove book
            3. Get book details
            4. Search books
            5. List all books
            6. Update book details
            7. Check book availability
            8. Exit
            --------------------

Enter your choice: 5
All books:
Title: abc
Author: abc
Publisher: abc
Volume: abc
Year: 2003
ISBN: 0


            --------------------
            MENU

            1. Add book
            2. Remove book
            3. Get book details
            4. Search books
            5. List all books
            6. Update book details
            7. Check book availability
            8. Exit
            --------------------

Enter your choice: 8
```

### 5. Write a Python program for a wether analyzing system to process and analyze weather data for Bangalore City from 20th July to 26th July in 2024. 
Each day’s data includes:
- Date 
- Maximum temperature (in Celsius) 
- Minimum temperature (in Celsius) 
- Humidity (in percentage)
1. Write a function that finds the average maximum and minimum temper atures from the weather data. 
2. Write a function that calculates the average humidity over the given period

OUTPUT:
```
weather_data = [
    {"date": "2024-08-01", "max_temp": 28, "min_temp": 20, "humidity": 75},
    {"date": "2024-08-02", "max_temp": 30, "min_temp": 22, "humidity": 80},
    {"date": "2024-08-03", "max_temp": 32, "min_temp": 24, "humidity": 85},
    {"date": "2024-08-04", "max_temp": 29, "min_temp": 21, "humidity": 70},
    {"date": "2024-08-05", "max_temp": 31, "min_temp": 23, "humidity": 75},
    {"date": "2024-08-06", "max_temp": 33, "min_temp": 25, "humidity": 80},
    {"date": "2024-08-07", "max_temp": 30, "min_temp": 22, "humidity": 75},
    {"date": "2024-08-08", "max_temp": 28, "min_temp": 20, "humidity": 70},
    {"date": "2024-08-09", "max_temp": 27, "min_temp": 19, "humidity": 65},
    {"date": "2024-08-10", "max_temp": 26, "min_temp": 18, "humidity": 60}
]

def find_highest_and_lowest_temperatures():
    highest_temp = float('-inf')
    lowest_temp = float('inf')
    for day in weather_data:
        max_temp = day["max_temp"]
        min_temp = day["min_temp"]
        if max_temp > highest_temp:
            highest_temp = max_temp
        if min_temp < lowest_temp:
            lowest_temp = min_temp
    return highest_temp, lowest_temp

def compute_average_humidity(start_date, end_date):
    total_humidity = 0
    days = 0
    for day in weather_data:
        if day["date"][8:] < start_date or day["date"][8:] > end_date:
            humidity = day["humidity"]
            total_humidity += humidity
            days += 1
    average_humidity = total_humidity / days
    return average_humidity

while True:
    print(
        """
        -------------------------
        Menu:
          1. Find highest and lowest temperatures
          2. Compute average humidity
          3. Exit
        -------------------------
        """
          )

    choice = input("Enter your choice (1-3): ")

    if choice == "1":
        highest_temp, lowest_temp = find_highest_and_lowest_temperatures()
        print("Highest temperature:", highest_temp)
        print("Lowest temperature:", lowest_temp)
    elif choice == "2":
        start_date = input("Enter start date only for August 2024(DD): ")
        end_date = input("Enter end date only for August 2024 (DD): ")

        average_humidity = compute_average_humidity(start_date, end_date)
        print("Average humidity:", average_humidity)
    elif choice == "3":
        print("Exiting...")
        break
    else:
        print("Invalid choice. Please try again.")
```