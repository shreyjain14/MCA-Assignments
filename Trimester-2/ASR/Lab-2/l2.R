# Q1: Create a vector of student names and marks of three subjects (Math, Science, and English) where rows represent students and columns represent subjects. Create a matrix student using the above vectors. Change the matrix so that subjects become rows and students become columns. 

# 1. Create vectors of student names and marks for three subjects
student_names <- c("Ananya", "Rohit", "Priya", "Vikram", "Sita", "Amit")
math_marks <- c(85, 78, 90, 88, 92, 84)
science_marks <- c(88, 85, 93, 91, 89, 87)
english_marks <- c(87, 83, 91, 92, 91, 85)

# 2. Create a matrix with students as columns and subjects as rows
student_matrix <- cbind(math_marks, science_marks, english_marks)
print("Student Matrix:")
print(student_matrix)

# 3. Changing the rows to cols and cols to rows
student_matrix <- t(student_matrix)
print("Student Matrix (Transposed):")
print(student_matrix)


# Q2: Create dataframes midterm and endterm with three students' names and marks for three subjects. Merge midterm and endterm by student names. Compute the total and average marks for each student, combining the midterm and endterm. Melt the merged data frame into a long format and Cast the data back to a wide format to analyze grade distributions.

# 1. Create data frames for midterm and endterm scores
midterm <- data.frame(
  Name = c("Ananya", "Rohit", "Priya"),
  Math = c(85, 78, 90),
  Science = c(88, 85, 93),
  English = c(87, 83, 91)
)
endterm <- data.frame(
  Name = c("Ananya", "Rohit", "Priya"),
  Math = c(88, 82, 91),
  Science = c(90, 87, 94),
  English = c(89, 85, 92)
)
print("Midterm Data:")
print(midterm)
print("Endterm Data:")
print(endterm)

# 2. Merge the data frames by student names
merged_data <- merge(midterm, endterm, by = "Name", suffixes = c("_Midterm", "_Endterm"))
print("Merged Data:")
print(merged_data)

# 3. Calculate total and average marks for each student
merged_data$Total <- rowSums(merged_data[, 2:7])
merged_data$Average <- rowMeans(merged_data[, 2:7])
print("Merged Data with Total and Average:")
print(merged_data)

# 4. Melt the data frame into a long format
library(reshape2)
melted_data <- melt(merged_data, id.vars = "Name", measure.vars = c("Math_Midterm", "Science_Midterm", "English_Midterm", "Math_Endterm", "Science_Endterm", "English_Endterm"), variable.name = "Subject", value.name = "Marks")
print("Melted Data:")
print(melted_data)

# 5. Cast the data back to a wide format
casted_data <- dcast(melted_data, Name ~ Subject, value.var = "Marks")
print("Casted Data:")
print(casted_data)

# 6. Analyze grade distributions
grade_distribution <- cut(merged_data$Average, breaks = c(0, 60, 70, 80, 90, 100), labels = c("F", "D", "C", "B", "A"))
merged_data$Grade <- grade_distribution
print("Merged Data with Grades:")
print(merged_data)