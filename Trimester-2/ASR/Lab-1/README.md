OUTPUT:
```
> # 1. Create vectors for employee names, departments, and performance scores for 3 quarters (Q1, Q2, Q3)
> employee_names <- c("Ananya", "Rohit", "Priya", "Vikram", "Sita", "Amit", "Neha", "Ravi", "Meera", "Karan", "Kavita", "Lakshman")
> departments <- c(rep("HR", 4), rep("IT", 4), rep("Sales", 4))
> 
> performance_Q1 <- c(85, 78, 90, 88, 92, 84, 95, 80, 70, 65, 88, 78)
> performance_Q2 <- c(88, 85, 93, 91, 89, 87, 94, 81, 75, 68, 84, 82)
> performance_Q3 <- c(87, 83, 91, 92, 91, 85, 96, 83, 74, 66, 86, 80)
> 
> # 2. Combine performance scores into a matrix
> performance_matrix <- cbind(performance_Q1, performance_Q2, performance_Q3)
> rownames(performance_matrix) <- employee_names
> 
> print("Performance Matrix:")
[1] "Performance Matrix:"
> print(performance_matrix)
         performance_Q1 performance_Q2 performance_Q3
Ananya               85             88             87
Rohit                78             85             83
Priya                90             93             91
Vikram               88             91             92
Sita                 92             89             91
Amit                 84             87             85
Neha                 95             94             96
Ravi                 80             81             83
Meera                70             75             74
Karan                65             68             66
Kavita               88             84             86
Lakshman             78             82             80
> 
> # 3. Convert the matrix into a data frame with employee names and department
> employee_data <- data.frame(Name = employee_names, Department = departments, performance_matrix)
> 
> print("Employee Data Frame:")
[1] "Employee Data Frame:"
> print(employee_data)
             Name Department performance_Q1 performance_Q2 performance_Q3
Ananya     Ananya         HR             85             88             87
Rohit       Rohit         HR             78             85             83
Priya       Priya         HR             90             93             91
Vikram     Vikram         HR             88             91             92
Sita         Sita         IT             92             89             91
Amit         Amit         IT             84             87             85
Neha         Neha         IT             95             94             96
Ravi         Ravi         IT             80             81             83
Meera       Meera      Sales             70             75             74
Karan       Karan      Sales             65             68             66
Kavita     Kavita      Sales             88             84             86
Lakshman Lakshman      Sales             78             82             80
> 
> # 4. Modify employee performance scores - Example: Change Ananya's Q1 score to 90
> employee_data$performance_Q1[employee_data$Name == "Ananya"] <- 90
> 
> print("Modified Employee Data Frame:")
[1] "Modified Employee Data Frame:"
> print(employee_data)
             Name Department performance_Q1 performance_Q2 performance_Q3
Ananya     Ananya         HR             90             88             87
Rohit       Rohit         HR             78             85             83
Priya       Priya         HR             90             93             91
Vikram     Vikram         HR             88             91             92
Sita         Sita         IT             92             89             91
Amit         Amit         IT             84             87             85
Neha         Neha         IT             95             94             96
Ravi         Ravi         IT             80             81             83
Meera       Meera      Sales             70             75             74
Karan       Karan      Sales             65             68             66
Kavita     Kavita      Sales             88             84             86
Lakshman Lakshman      Sales             78             82             80
> 
> # 5. Calculate the average performance score per employee
> employee_data$Average <- rowMeans(employee_data[, 3:5])
> 
> print("Employee Data Frame with Averages:")
[1] "Employee Data Frame with Averages:"
> print(employee_data)
             Name Department performance_Q1 performance_Q2 performance_Q3
Ananya     Ananya         HR             90             88             87
Rohit       Rohit         HR             78             85             83
Priya       Priya         HR             90             93             91
Vikram     Vikram         HR             88             91             92
Sita         Sita         IT             92             89             91
Amit         Amit         IT             84             87             85
Neha         Neha         IT             95             94             96
Ravi         Ravi         IT             80             81             83
Meera       Meera      Sales             70             75             74
Karan       Karan      Sales             65             68             66
Kavita     Kavita      Sales             88             84             86
Lakshman Lakshman      Sales             78             82             80
          Average
Ananya   88.33333
Rohit    82.00000
Priya    91.33333
Vikram   90.33333
Sita     90.66667
Amit     85.33333
Neha     95.00000
Ravi     81.33333
Meera    73.00000
Karan    66.33333
Kavita   86.00000
Lakshman 80.00000
> 
> # 6. Add a new employee's performance data
> new_employee <- data.frame(Name = "Rakesh", Department = "IT", performance_Q1 = 85, performance_Q2 = 89, performance_Q3 = 87)
> new_employee$Average <- rowMeans(new_employee[, 3:5])
> 
> # Add the new employee to the data frame
> employee_data <- rbind(employee_data, new_employee)
> 
> print("Employee Data Frame with New Employee (Rakesh):")
[1] "Employee Data Frame with New Employee (Rakesh):"
> print(employee_data)
             Name Department performance_Q1 performance_Q2 performance_Q3
Ananya     Ananya         HR             90             88             87
Rohit       Rohit         HR             78             85             83
Priya       Priya         HR             90             93             91
Vikram     Vikram         HR             88             91             92
Sita         Sita         IT             92             89             91
Amit         Amit         IT             84             87             85
Neha         Neha         IT             95             94             96
Ravi         Ravi         IT             80             81             83
Meera       Meera      Sales             70             75             74
Karan       Karan      Sales             65             68             66
Kavita     Kavita      Sales             88             84             86
Lakshman Lakshman      Sales             78             82             80
1          Rakesh         IT             85             89             87
          Average
Ananya   88.33333
Rohit    82.00000
Priya    91.33333
Vikram   90.33333
Sita     90.66667
Amit     85.33333
Neha     95.00000
Ravi     81.33333
Meera    73.00000
Karan    66.33333
Kavita   86.00000
Lakshman 80.00000
1        87.00000
> 
> # 7. Organize the data into a list for easy access
> employee_list <- list(
+   Names = employee_names,
+   Departments = departments,
+   Performance_Matrix = performance_matrix,
+   Employee_Data_Frame = employee_data
+ )
> 
> print("List containing employee data and performance matrix:")
[1] "List containing employee data and performance matrix:"
> print(employee_list)
$Names
 [1] "Ananya"   "Rohit"    "Priya"    "Vikram"   "Sita"     "Amit"    
 [7] "Neha"     "Ravi"     "Meera"    "Karan"    "Kavita"   "Lakshman"

$Departments
 [1] "HR"    "HR"    "HR"    "HR"    "IT"    "IT"    "IT"    "IT"    "Sales"
[10] "Sales" "Sales" "Sales"

$Performance_Matrix
         performance_Q1 performance_Q2 performance_Q3
Ananya               85             88             87
Rohit                78             85             83
Priya                90             93             91
Vikram               88             91             92
Sita                 92             89             91
Amit                 84             87             85
Neha                 95             94             96
Ravi                 80             81             83
Meera                70             75             74
Karan                65             68             66
Kavita               88             84             86
Lakshman             78             82             80

$Employee_Data_Frame
             Name Department performance_Q1 performance_Q2 performance_Q3
Ananya     Ananya         HR             90             88             87
Rohit       Rohit         HR             78             85             83
Priya       Priya         HR             90             93             91
Vikram     Vikram         HR             88             91             92
Sita         Sita         IT             92             89             91
Amit         Amit         IT             84             87             85
Neha         Neha         IT             95             94             96
Ravi         Ravi         IT             80             81             83
Meera       Meera      Sales             70             75             74
Karan       Karan      Sales             65             68             66
Kavita     Kavita      Sales             88             84             86
Lakshman Lakshman      Sales             78             82             80
1          Rakesh         IT             85             89             87
          Average
Ananya   88.33333
Rohit    82.00000
Priya    91.33333
Vikram   90.33333
Sita     90.66667
Amit     85.33333
Neha     95.00000
Ravi     81.33333
Meera    73.00000
Karan    66.33333
Kavita   86.00000
Lakshman 80.00000
1        87.00000

> 
> # 8. Delete the performance data for one employee - Example: Lakshman
> employee_data <- employee_data[employee_data$Name != "Lakshman", ]
> 
> print("Employee Data Frame after removing Lakshman:")
[1] "Employee Data Frame after removing Lakshman:"
> print(employee_data)
         Name Department performance_Q1 performance_Q2 performance_Q3  Average
Ananya Ananya         HR             90             88             87 88.33333
Rohit   Rohit         HR             78             85             83 82.00000
Priya   Priya         HR             90             93             91 91.33333
Vikram Vikram         HR             88             91             92 90.33333
Sita     Sita         IT             92             89             91 90.66667
Amit     Amit         IT             84             87             85 85.33333
Neha     Neha         IT             95             94             96 95.00000
Ravi     Ravi         IT             80             81             83 81.33333
Meera   Meera      Sales             70             75             74 73.00000
Karan   Karan      Sales             65             68             66 66.33333
Kavita Kavita      Sales             88             84             86 86.00000
1      Rakesh         IT             85             89             87 87.00000
> 
```