list1[1]
list1<-list("Asha","Azar","Anita")
empID<-c(100,102,103,104)#what happens here?

#naming elements of the list
list1<-list(c("Jan", "Feb"), matrix(c(2,4,5,6),nrow=2),list("red",15.8))
list1

#give names to elements of the list
names(list1)<-c("Months","Matrix","list2")
names(list1)

#access elements if the list
list1[3]
list1[2]

#we can also access list using its name
list1$list2

#adding elements to the end of the list
list1[4]<-"New_data"
list1

#remove elements from the list
list1[3]<-NULL
list1[3]

#merging two lists
list1<-list(1,2,3)
list2<-list("M","T","W")
mergedlist<-c(list1,list2)
mergedlist

#converting list to vector -using unlist command
v1<-unlist(list1)
v1

#creating 2 lists and adding them
#Adding Numeric Vectors Stored in Lists
# Create two lists with numeric vectors
list1 <- list(a = c(1, 2, 3), b = c(4, 5, 6))
list2 <- list(a = c(10, 20, 30), b = c(40, 50, 60))

# Element-wise addition by accessing the list elements
result <- list(a = list1$a + list2$a, b = list1$b + list2$b)

# Print the result
print(result)

#Unlist the Lists and Add Them
# Create two lists
list1 <- list(1, 2, 3)
list2 <- list(4, 5, 6)

# Unlist and add
result <- unlist(list1) + unlist(list2)

# Print the result
print(result)

#Adding Lists of Non-Numeric Data
# Create two lists with different data types
list1 <- list("apple", "banana", 10)
list2 <- list("orange", "grape", 20)

# Concatenate the lists
result <- c(list1, list2)

# Print the result
print(result)

#there are many ways tp check the datatype
#class(),typeof(),str(), is.<type()


#The class() function returns the class (or classes) of an object, indicating how R understands the object.

x <- 42
class_x <- class(x)
print(class_x)  # Output: "numeric"

y <- "Hello"
class_y <- class(y)
print(class_y)  # Output: "character"


#The typeof() function returns the low-level type of an R object, providing more specific information about how the data is stored internally.

z <- TRUE
typeof_z <- typeof(z)
print(typeof_z)  # Output: "logical"

a <- 3.14
typeof_a <- typeof(a)
print(typeof_a)  # Output: "double"

#The str() function (short for structure) gives a compact display of the internal structure of an R object, showing data types, lengths, and elements in a concise format.
# Example
my_list <- list(name = "Alice", age = 30, scores = c(85, 90, 95))
str(my_list)

#creating a matrix
m1<-matrix(c(2:13),nrow=4,byrow=FALSE)
m1
#define names for these rows and columns
rownames<-(c("R1", "R2","R3","R4"))
colnames<-(c("C1","C2","C3"))
m1<-matrix(c(2:13,nrow=4,byrow=FALSE,dimnames=list(rownames,colnames)))
m1

#access two elements of a matrix
#create two matrices and perform arithmetic oeprations on them
#matrix1 x matrix2 and so on
#HW: Create an array and perform operations on this
#Factors- data objects which are used to categorize data and arrange it as levels they can be strings or integers

data<-c("N","S","E","W")
is.factor(data)
#we are getting false, so lets convert into factor
factor_data<-factor(data)
is.factor(factor_data)
#why do we need factors?
#give an example each of lists , vectors, arrays, and matrices in real life

# Create a data frame and assign it to the variable 'emp.data'
emp.data <- data.frame(emp_ID = c(1:5), 
                       emp_Name = c("Azar", "Anita", "Jitu", "Rima", "Tara"),
                       emp_Age = c(25, 30, 28, 22, 35))

# View the data frame
emp.data  

# Structure of the dataframe
str(emp.data)

# Summary of the dataframe
summary(emp.data)

#what is the difference between character and factor?
 #Extract specific column from the dataframe
# Extract the 'emp_Name' column
emp_names <- emp.data$emp_Name

# Print the 'emp_Name' column
print(emp_names)

#extract first 2 rows
first_two_rows <- emp.data[1:2, ]
print(first_two_rows)

#extract 3rd and 5th row, 2nd and 3rd column
specific_rows_columns <- emp.data[c(3, 5), c(2, 3)]
print(specific_rows_columns)

#HW: To convert any numerical data like ID, House numbers,etc. We convert to character or factor (as. character or as. factor)

#Expand the dataframe
emp.data$dept <- c("HR", "Finance", "IT", "Marketing", "Sales")
print(emp.data)
str(emp.data)
summary(emp.data)

#add a new row, use rbind
new_row <- data.frame(emp_ID = 6, emp_Name = "John", emp_Age = 29, dept = "Operations")
emp.data <- rbind(emp.data, new_row)
print(emp.data)

#we can also use cbind function to add more columns to a dataframe, cbind can also be used to combine vectors, matrices by column.