library(ggplot2)

data = read.csv("C:\\Users\\Shrey\\Downloads\\training_productivity_anova.csv")

summary(data)

# one way anova
anova_one_way <- aov(Productivity ~ Training_Method, data = data)
summary(anova_one_way)

# two way anova
anova_two_way <- aov(Productivity ~ Training_Method * Department, data = data)
summary(anova_two_way)

# boxplot
boxplot(Productivity ~ Training_Method, data = data,
        main = "Boxplot of Productivity by Training Method",
        xlab = "Training Method", ylab = "Productivity", col = "skyblue")

boxplot(Productivity ~ Department, data = data,
        main = "Boxplot of Productivity by Department",
        xlab = "Department", ylab = "Productivity", col = "lightgreen")

# density plot
# For Training_Method
ggplot(data, aes(x = Productivity, fill = Training_Method)) +
  geom_density(alpha = 0.5) +
  labs(title = "Density Plot of Productivity by Training Method",
       x = "Productivity", y = "Density") +
  theme_minimal()

# For Department
ggplot(data, aes(x = Productivity, fill = Department)) +
  geom_density(alpha = 0.5) +
  labs(title = "Density Plot of Productivity by Department",
       x = "Productivity", y = "Density") +
  theme_minimal()
