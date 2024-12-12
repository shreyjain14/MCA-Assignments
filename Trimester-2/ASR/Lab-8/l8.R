#load dataset
data <- read.csv("C:\\Users\\shrey\\Downloads\\study_exam_data.csv")


# 1. Fit a simple linear regression model
model <- lm(Exam_Score ~ Study_Hours, data = data)

# Summary of the model
summary(model)

# Extract regression coefficients, R-squared, and p-value
coefficients <- coef(model)
r_squared <- summary(model)$r.squared
p_value <- summary(model)$coefficients[2, 4]

# Print the results

# Regression Equation
cat("ExamScore =", round(coefficients[1], 2), "+", round(coefficients[2], 2), "* StudyHours\n")

# R-Squared
round(r_squared, 4)

# P-value for slope
round(p_value, 4)

# 2. Plot the data and the regression line
plot(data$Study_Hours, data$Exam_Score, 
     main = "Regression of Exam Scores on Study Hours",
     xlab = "Study Hours per Week",
     ylab = "Exam Score",
     col = "blue", pch = 16)
abline(model, col = "red", lwd = 2)

# 4. Predict the exam score for a student who studies 10 hours per week
predicted_score <- predict(model, newdata = data.frame(Study_Hours = 10))

# Predicted exam score for 10 study hours
round(predicted_score, 2)
