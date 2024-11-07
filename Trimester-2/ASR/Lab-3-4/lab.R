#load the csv file
mortality_data <- read.csv("C:/Users/Shrey/Desktop/CODING/mca/Trimester-2/ASR/Lab-3-4/mortality.csv")

#preview the data 
head(mortality_data)
str(mortality_data)


#LAB3:Descriptive Statistics
#A. Count the number of Variables and Observations
n_variables <- ncol(mortality_data)  # Number of variables (columns)
n_observations <- nrow(mortality_data)  # Number of observations (rows)
n_variables
n_observations

#B.Extract variables 2 and 3 as Vectors
variable2 <- mortality_data[[2]]
variable3 <- mortality_data[[3]]
variable2
variable3

#C.Count different Blood groups
diff_blood_groups <- length(unique(mortality_data$BLOOD))
diff_blood_groups

#D. Unique smoke categories
unique_smoke <- unique(mortality_data$SMOKE)
unique_smoke

#E.Cholesterol levels above 300
high_chol <- sum(mortality_data$CHOL > 300)
high_chol

#F.Mean height value for mortality is alive
mean_height_alive <- mean(mortality_data$HEIGHT[mortality_data$MORT == "alive"], na.rm = TRUE)
mean_height_alive

#G.Age of the tallest O-Blood Group person.
tallest_o <- mortality_data[mortality_data$BLOOD == "o" & !is.na(mortality_data$HEIGHT), ]
age_tallest_O <- tallest_o$AGE[which.max(tallest_o$HEIGHT)]
age_tallest_O

#H.Nonsmokers are alive who are below 40 years
nonsmokers_alive <- sum(mortality_data$SMOKE == "nonsmo" & mortality_data$MORT == "alive" & mortality_data$AGE < 40, na.rm = TRUE)
nonsmokers_alive



#LAB4:Plots & Charts

#A.Using a single variable, draw a bar plot, pie chart, box plot, and histogram
#Smoke and height 
#Bar Plot of Smoke cateogry
barplot(table(mortality_data$SMOKE), 
        main = "Bar Plot of Smoke Categories", 
        xlab = "Smoke Category", 
        ylab = "Count", 
        col = "red")

#Pie Chart of Blood groups
blood_counts <- table(mortality_data$BLOOD)
pie(blood_counts, 
    main = "Pie Chart of Blood Groups", 
    col = rainbow(length(blood_counts)))

#Box Plot of Height category
boxplot(mortality_data$HEIGHT, 
        main = "Box Plot of Height", 
        ylab = "Height", 
        col = "red")

#Histogram of Height category
hist(mortality_data$HEIGHT, 
     main = "Histogram of Height", 
     xlab = "Height", 
     ylab = "Frequency", 
     col = "red", 
     breaks = 20)

#B.Using two variables, draw a bar plot, scatter plot, and box plot
#Bar Plot of Smoke and Blood
# Create a table of counts for SMOKE and BLOOD
smoke_blood_counts <- table(mortality_data$SMOKE, mortality_data$BLOOD)
barplot(smoke_blood_counts, 
        beside = TRUE, 
        main = "Bar Plot of Smoke vs Blood Group", 
        xlab = "Smoke Category", 
        ylab = "Count", 
        col = c("red", "green", "blue"))
legend("topright", legend = rownames(smoke_blood_counts), fill = c("red", "green", "blue"))

#Scatter Plot of Height and Weight
plot(mortality_data$HEIGHT, mortality_data$WEIGHT, 
     main = "Scatter Plot of Height vs Weight", 
     xlab = "Height", 
     ylab = "Weight", 
     col = "red", 
     pch = 16)

#Box Plot of Weight Grouped By Smoke
boxplot(mortality_data$WEIGHT ~ mortality_data$SMOKE, 
        main = "Box Plot of Weight by Smoke Category", 
        xlab = "Smoke Category", 
        ylab = "Weight", 
        col = "red")


#C. Using the multivariable draw a bar plot and pair plot
#Bar Plot of Smoke and Blood and Mortality status
# Count combinations of SMOKE and MORT categories within each BLOOD group
smoke_mort_counts <- table(mortality_data$SMOKE, mortality_data$MORT)
barplot(smoke_mort_counts, 
        beside = TRUE, 
        col = c("red", "green", "blue"), 
        main = "Bar Plot of Smoke vs Mortality by Blood Group", 
        xlab = "MORT", 
        ylab = "Count")

#Pair Plot
pairs(mortality_data[, c("AGE", "HEIGHT", "WEIGHT", "CHOL")], 
      main = "Pair Plot of Age, Height, Weight, and Cholesterol")