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


#Exercise- skewness, variance, etc.
library(e1071)

# Function to calculate spread and shape statistics for each coffee type
cafe_stats <- function(data, coffee_type) {
  # Filter data for the specified coffee type
  coffee_data <- data[data$Coffee_Type == coffee_type, "Cups_Sold"]
 
  # Calculate spread (Range, Variance, Standard Deviation)
  range_sales <- diff(range(coffee_data))    # Range
  variance_sales <- var(coffee_data)         # Variance
  sd_sales <- sd(coffee_data)                # Standard Deviation
 
  # Calculate skewness and kurtosis
  skewness_sales <- skewness(coffee_data)
  kurtosis_sales <- kurtosis(coffee_data)
 
  # Display results
  cat("\nStatistics for", coffee_type, ":\n")
  cat("Range of cups sold:", range_sales, "\n")
  cat("Variance of cups sold:", variance_sales, "\n")
  cat("Standard Deviation of cups sold:", sd_sales, "\n")
  cat("Skewness of cups sold:", skewness_sales, "\n")
  cat("Kurtosis of cups sold:", kurtosis_sales, "\n")
}

# Apply the stats function to each coffee type
for (type in unique(coffee_sales$Coffee_Type)) {
  cafe_stats(coffee_sales, type)
}