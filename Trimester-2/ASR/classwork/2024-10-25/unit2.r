#UNIT-2

#Define statistics and explain why it is important for understanding and evaluating data
#Statistics is the science of collecting, analyzing, interpreting, presenting, and organizing data.
#It is essential for data analytics because it provides the foundational tools and methods for making sense of large and complex datasets.
#Statistics helps analysts make informed decisions by summarizing and interpreting data trends and patterns.
#Statistical methods enable analysts to test hypotheses and validate assumptions, ensuring that conclusions drawn from data are reliable.
#Statistical models help in forecasting future trends based on historical data, aiding in strategic planning and decision-making.

#Whole numbers or counts
discrete_data <- c(3, 7, 10, 2, 5)

#Any value within a range.
continuous_data <- c(1.2, 3.5, 4.8, 6.9, 2.3)

#Qualitative data represents categories or labels.
#Categories with no specific order (e.g., colors, brands)
nominal_data <- factor(c("Red", "Blue", "Green"))
#Categories with a meaningful order (e.g., rating scales)
ordinal_data <- factor(c("Low", "Medium", "High"), ordered=TRUE)

#Multivariate data consists of more than one variable. In R, multivariate data is often represented as a .
multivariate_data <- data.frame(
  height = c(170, 165, 180),
  weight = c(65, 70, 75),
  gender = c("Male", "Female", "Male")
)

#Exercise questions:
#1. School Library Borrowed Books
# Create a variable for borrowed books by genre
library_books <- data.frame(
  Genre = c("Fiction", "Non-Fiction", "Science", "History", "Technology"),
  Books_Borrowed = c(120, 80, 90, 70, 60)
)
# Display the dataset
print(library_books)


#2.Favourite fruits of students survey
# Create a dataset for favorite fruits of 50 students
set.seed(42)
students_fruit <- data.frame(
  Student_ID = 1:50,
  Favorite_Fruit = sample(c("Apples", "Bananas", "Oranges", "Grapes"), 50, replace = TRUE)
)
# Display the dataset
print(table(students_fruit$Favorite_Fruit)) # Show count of each fruit

#3.Daily Rainfall Recorded
# Create a variable for daily rainfall over a week
daily_rainfall <- data.frame(
  Day = c("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"),
  Rainfall_mm = c(12, 5, 0, 20, 15, 10, 0)
)
# Display the dataset
print(daily_rainfall)

#4.Employee Performance Evaluation
# Create a variable for employee performance evaluation
set.seed(123) # For reproducibility
employee_performance <- data.frame(
  Employee_ID = 1:10,
  Tasks_Completed = sample(10:50, 10, replace = TRUE),
  Hours_Worked = sample(30:50, 10, replace = TRUE),
  Satisfaction_Rating = sample(1:10, 10, replace = TRUE)
)
# Display the dataset
print(employee_performance)


#Exercise - Cafe example
# Define the types of coffee
coffee_types <- c("Espresso", "Latte", "Cappuccino", "Americano", "Mocha")

# Generate random data for each coffee type for a week (7 days)
set.seed(123)  # Setting seed for reproducibility
coffee_sales <- data.frame(
  Day = rep(1:7, each = 5),
  Coffee_Type = rep(coffee_types, 7),
  Cups_Sold = sample(20:100, 35, replace = TRUE)
)

# Display the data frame
print(coffee_sales)

# Calculate the mean, median, and mode of the cups sold for each coffee type

# Mean
mean_sales <- aggregate(Cups_Sold ~ Coffee_Type, data = coffee_sales, mean)
colnames(mean_sales)[2] <- "Mean_Cups_Sold"
print(mean_sales)

# Median
median_sales <- aggregate(Cups_Sold ~ Coffee_Type, data = coffee_sales, median)
colnames(median_sales)[2] <- "Median_Cups_Sold"
print(median_sales)

# Mode function
mode_function <- function(x) {
  uniq_x <- unique(x)
  uniq_x[which.max(tabulate(match(x, uniq_x)))]
}

# Calculate mode for each coffee type
mode_sales <- aggregate(Cups_Sold ~ Coffee_Type, data = coffee_sales, mode_function)
colnames(mode_sales)[2] <- "Mode_Cups_Sold"
print(mode_sales)

