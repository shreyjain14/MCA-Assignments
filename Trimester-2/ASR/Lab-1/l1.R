# 1. Create vectors for employee names, departments, and performance scores for 3 quarters (Q1, Q2, Q3)
employee_names <- c("Ananya", "Rohit", "Priya", "Vikram", "Sita", "Amit", "Neha", "Ravi", "Meera", "Karan", "Kavita", "Lakshman")
departments <- c(rep("HR", 4), rep("IT", 4), rep("Sales", 4))

performance_Q1 <- c(85, 78, 90, 88, 92, 84, 95, 80, 70, 65, 88, 78)
performance_Q2 <- c(88, 85, 93, 91, 89, 87, 94, 81, 75, 68, 84, 82)
performance_Q3 <- c(87, 83, 91, 92, 91, 85, 96, 83, 74, 66, 86, 80)

# 2. Combine performance scores into a matrix
performance_matrix <- cbind(performance_Q1, performance_Q2, performance_Q3)
rownames(performance_matrix) <- employee_names

print("Performance Matrix:")
print(performance_matrix)

# 3. Convert the matrix into a data frame with employee names and department
employee_data <- data.frame(Name = employee_names, Department = departments, performance_matrix)

print("Employee Data Frame:")
print(employee_data)

# 4. Modify employee performance scores - Example: Change Ananya's Q1 score to 90
employee_data$performance_Q1[employee_data$Name == "Ananya"] <- 90

print("Modified Employee Data Frame:")
print(employee_data)

# 5. Calculate the average performance score per employee
employee_data$Average <- rowMeans(employee_data[, 3:5])

print("Employee Data Frame with Averages:")
print(employee_data)

# 5.1. Calculate the average performance score per department
department_averages <- aggregate(employee_data[, 3:5], by = list(employee_data$Department), FUN = mean)
print("Department-wise Average Performance Scores:")
print(department_averages)

# 6. Add a new employee's performance data
new_employee <- data.frame(Name = "Rakesh", Department = "IT", performance_Q1 = 85, performance_Q2 = 89, performance_Q3 = 87)
new_employee$Average <- rowMeans(new_employee[, 3:5])

# Add the new employee to the data frame
employee_data <- rbind(employee_data, new_employee)

print("Employee Data Frame with New Employee (Rakesh):")
print(employee_data)

# 7. Organize the data into a list for easy access
employee_list <- list(
  Names = employee_names,
  Departments = departments,
  Performance_Matrix = performance_matrix,
  Employee_Data_Frame = employee_data
)

print("List containing employee data and performance matrix:")
print(employee_list)

# 8. Delete the performance data for one employee - Example: Lakshman
employee_data <- employee_data[employee_data$Name != "Lakshman", ]

print("Employee Data Frame after removing Lakshman:")
print(employee_data)

