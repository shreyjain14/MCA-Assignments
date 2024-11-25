# Load necessary libraries
library(ggplot2)
library(dplyr)
library(tidyr)
library(caret)
library(GGally)

# Read the dataset
user_data <- read.csv("C:/Users/shrey/Documents/CODING/MCA-Assignments/Trimester-2/ASR/Lab-5/user_behavior_dataset.csv")

# A 1V

# 1. Bar Plot (User Behavior Class as a categorical variable)
ggplot(user_data, aes(x = as.factor(User.Behavior.Class))) +
  geom_bar(fill = "skyblue") +
  labs(title = "Bar Plot of User Behavior Class", x = "User Behavior Class", y = "Count")

# 2. Pie Chart (Distribution of Gender)
gender_count <- table(user_data$Gender)
gender_percent <- round(gender_count / sum(gender_count) * 100)
gender_labels <- paste(names(gender_percent), gender_percent, "%", sep = " ")
pie(gender_count, labels = gender_labels, main = "Gender Distribution")

# 3. Box Plot (App Usage Time by User Behavior Class)
ggplot(user_data, aes(x = as.factor(User.Behavior.Class), y = App.Usage.Time..min.day.)) +
  geom_boxplot(fill = "lightgreen") +
  labs(title = "Box Plot of App Usage Time by User Behavior Class", x = "User Behavior Class", y = "App Usage Time (min/day)")

# 4. Density Plot (App Usage Time)
ggplot(user_data, aes(x = App.Usage.Time..min.day.)) +
  geom_density(fill = "purple", alpha = 0.5) +
  labs(title = "Density Plot of App Usage Time", x = "App Usage Time (min/day)", y = "Density")

# 5. Histogram (App Usage Time)
ggplot(user_data, aes(x = App.Usage.Time..min.day.)) +
  geom_histogram(bins = 15, fill = "orange", color = "black") +
  labs(title = "Histogram of App Usage Time", x = "App Usage Time (min/day)", y = "Frequency")

# B 2V

# 1. Bar Plot (User Behavior Class by Gender)
ggplot(user_data, aes(x = as.factor(User.Behavior.Class), fill = Gender)) +
  geom_bar(position = "dodge") +
  labs(title = "Bar Plot of User Behavior Class by Gender", x = "User Behavior Class", y = "Count")

# 2. Scatter Plot (App Usage Time vs. Age)
ggplot(user_data, aes(x = Age, y = App.Usage.Time..min.day.)) +
  geom_point(aes(color = Gender), size = 3) +
  labs(title = "Scatter Plot of App Usage Time vs. Age", x = "Age", y = "App Usage Time (min/day)")

# 3. Violin Plot (App Usage Time by User Behavior Class)
ggplot(user_data, aes(x = as.factor(User.Behavior.Class), y = App.Usage.Time..min.day.)) +
  geom_violin(fill = "lightpink") +
  labs(title = "Violin Plot of App Usage Time by User Behavior Class", x = "User Behavior Class", y = "App Usage Time (min/day)")

# 4. Box Plot (App Usage Time by Gender)
ggplot(user_data, aes(x = Gender, y = App.Usage.Time..min.day.)) +
  geom_boxplot(fill = "lightblue") +
  labs(title = "Box Plot of App Usage Time by Gender", x = "Gender", y = "App Usage Time (min/day)")

# C MV

# 1. Bar Plot (Device Model vs User Behavior Class)
ggplot(user_data, aes(x = Device.Model, fill = as.factor(User.Behavior.Class))) +
  geom_bar(position = "dodge") +
  labs(title = "Bar Plot of Device Model by User Behavior Class", x = "Device Model", y = "Count")

# Meta-Data and Inferences:

# Bar Plot: Compares the frequencies of categories within a variable. For example, User Behavior Class distribution by Device Model or Gender.
# Pie Chart: Shows proportions of a single variable, such as the gender distribution.
# Box Plot: Highlights the spread and any outliers in App Usage Time across different User Behavior Classes.
# Density Plot: Displays the distribution of App Usage Time, showing peaks and spread.
# Histogram: Shows frequency distribution of App Usage Time, indicating common usage times.
# Scatter Plot: Shows the relationship between App Usage Time and Age, color-coded by Gender, revealing trends.
# Violin Plot: Combines aspects of a box plot and density plot to show the distribution and spread of App Usage Time by User Behavior Class.
# Pair Plot: Visualizes pairwise relationships between numeric variables, revealing correlations or trends.

