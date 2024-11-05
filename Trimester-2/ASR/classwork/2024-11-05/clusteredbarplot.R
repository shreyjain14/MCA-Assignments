# Create a 4x4 matrix with sample data
data <- matrix(c(12, 15, 8, 10,
                 7, 13, 11, 9,
                 14, 6, 12, 8,
                 9, 11, 7, 13), 
               nrow = 4, ncol = 4)

# Add row and column names
rownames(data) <- c("Group A", "Group B", "Group C", "Group D")
colnames(data) <- c("Q1", "Q2", "Q3", "Q4")

# Define colors for bars
colors <- c("red", "blue", "green", "yellow")

# Create the stacked barplot
par(mar = c(5, 4, 4, 8))  # Adjust margins to make room for legend

barplot(data,
        beside = FALSE,
        col = colors,  
        legend.text = colnames(data),  
        args.legend = list(x = "topright", 
                           inset = c(-0.2, 0),
                           title = "Quarters"),
        main = "Quarterly Performance by Group",
        xlab = "Groups",
        ylab = "Total Performance Score",
        names.arg = rownames(data))


