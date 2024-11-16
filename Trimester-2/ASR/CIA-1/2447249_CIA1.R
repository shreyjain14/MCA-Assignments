# Q1

# load the data
data <- read.csv("C:/Users/shrey/Documents/CODING/MCA-Assignments/Trimester-2/ASR/CIA-1/SalaryDataset.csv")

# set the header of the dataset
head(data)

# convert the dataset into strings
str(data)

# save the number of columns in n_var
n_var <- ncol(data)

# save the number of observations in n_dat
n_dat <- nrow(data)

# printing the number of rows and cols
print("Number of Rowss:")
n_var
print("NUmber of Columns:")
n_dat

# barplot of the gender based diversification
barplot(table(data$Gender))

# histogram of the salaries
hist(data$Salary)

# pie chart of gender diversification
pie(table(data$Gender))

# changing the data of a temp copy of data
temp <- data
temp[[1]][1]
temp[[1]][1] <- "female"
temp[[1]][1]

# sum of all the salaries
sum(data$Salary)

# product of salaries and rank arrays
outer(data$Salary, data$Rank)


# Q2

salaryMean <- mean(data$Salary)
salaryMedian <- median(data$Salary)
salarySD <- sd(data$Salary)
salaryVariance <- salarySD * salarySD


print("Mean: ")
salaryMean

print("Median: ")
salaryMedian

print("Variance: ")
salaryVariance

print("Standard Variance: ")
salarySD

q2_data <- subset(data, data$Gender == "male")
q2_data <- subset(q2_data, q2_data$HighDegree == 1)
q2_data <- subset(q2_data, q2_data$Years.Degree > 20)
q2_data

# Q3

dotchart(subset(data$Salary, data$Gender == "male"))
dotchart(subset(data$Salary, data$Gender == "female"))

# we can see the outlier variable in female is the one with near 37000 salary
# the insights we can see are most of the male's salary do not have any major outlier where as in female's there are 2 obvious outliers


copyData <- data


