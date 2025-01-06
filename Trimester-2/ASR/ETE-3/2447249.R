# Load necessary libraries
library(ggplot2)
library(dplyr)
library(plotly)

# Load the dataset
spotify <- read.csv("C:\\spotify_tracks.csv")

# Data cleaning: Remove missing values and select relevant columns
spotify_clean <- spotify %>%
  filter(!is.na(liveness), !is.na(instrumentalness), !is.na(key), !is.na(duration_ms)) %>%
  select(liveness, instrumentalness, key, duration_ms)

# Display structure of cleaned data
str(spotify_clean)

# Interpretation: 
# The dataset contains numeric variables `liveness`, `instrumentalness`, and `duration_ms` 
# and a categorical variable `key` (representing musical key, 0-11).

### 1. Descriptive Statistics ###
# Calculate statistics for liveness
mean_liveness <- mean(spotify_clean$liveness)
median_liveness <- median(spotify_clean$liveness)
mode_liveness <- as.numeric(names(sort(table(spotify_clean$liveness), decreasing = TRUE)[1]))
sd_liveness <- sd(spotify_clean$liveness)
var_liveness <- var(spotify_clean$liveness)

# Calculate statistics for instrumentalness
mean_instrumentalness <- mean(spotify_clean$instrumentalness)
median_instrumentalness <- median(spotify_clean$instrumentalness)
mode_instrumentalness <- as.numeric(names(sort(table(spotify_clean$instrumentalness), decreasing = TRUE)[1]))
sd_instrumentalness <- sd(spotify_clean$instrumentalness)
var_instrumentalness <- var(spotify_clean$instrumentalness)

# Print Descriptive Statistics
cat("Descriptive Statistics for Liveness:\n")
cat("Mean:", mean_liveness, "Median:", median_liveness, "Mode:", mode_liveness,
    "Standard Deviation:", sd_liveness, "Variance:", var_liveness, "\n")
cat("Descriptive Statistics for Instrumentalness:\n")
cat("Mean:", mean_instrumentalness, "Median:", median_instrumentalness, "Mode:", mode_instrumentalness,
    "Standard Deviation:", sd_instrumentalness, "Variance:", var_instrumentalness, "\n")

# Interpretation:
# The statistics provide a comprehensive summary of the central tendency and dispersion for `liveness` and `instrumentalness`.
# For example, the mean indicates the average level, and the standard deviation shows the variability.

### 2. Inferential Statistics ###

# a. One-Sample T-test for Liveness
# Hypotheses:
# H0: Mean liveness = 0.2
# H1: Mean liveness ≠ 0.2
t_test_one_sample <- t.test(spotify_clean$liveness, mu = 0.2)
print(t_test_one_sample)

# Interpretation:
# If the p-value is < 0.05, reject H0, indicating the mean liveness significantly differs from 0.2.

# b. Independent Two-Sample T-test: High vs Low Instrumentalness for Duration
# Hypotheses:
# H0: Mean duration_ms for High and Low instrumentalness groups is the same.
# H1: Mean duration_ms differs between groups.
spotify_clean$instrumentalness_group <- ifelse(spotify_clean$instrumentalness > 0.5, "High", "Low")
t_test_two_sample <- t.test(duration_ms ~ instrumentalness_group, data = spotify_clean)
print(t_test_two_sample)

# Interpretation:
# A p-value < 0.05 suggests a significant difference in track duration between high and low instrumentalness groups.

# c. Paired-Sample T-test: Instrumentalness and Liveness
# Hypotheses:
# H0: No difference in mean between instrumentalness and liveness.
# H1: Difference exists in mean between instrumentalness and liveness.
t_test_paired <- t.test(spotify_clean$instrumentalness, spotify_clean$liveness, paired = TRUE)
print(t_test_paired)

# Interpretation:
# A p-value < 0.05 indicates a significant difference between instrumentalness and liveness means.

# d. One-Way ANOVA for Duration_ms Across Keys
anova_one_way <- aov(duration_ms ~ factor(key), data = spotify_clean)
summary(anova_one_way)

# Interpretation:
# A significant p-value indicates that track durations vary significantly across musical keys.

# e. Linear Regression: Liveness as a Predictor of Duration_ms
lm_model <- lm(duration_ms ~ liveness, data = spotify_clean)
summary(lm_model)

# Interpretation:
# Coefficients and p-values indicate whether liveness significantly predicts track duration.

### 3. Data Visualizations ###

# Single-variable: Bar plot of Key
ggplot(spotify_clean, aes(x = factor(key))) +
  geom_bar(fill = "skyblue", color = "black") +
  labs(title = "Distribution of Musical Keys", x = "Key (0-11)", y = "Count") +
  theme_minimal()

# Interpretation:
# Displays the frequency of tracks in each musical key. Peaks indicate commonly used keys.

# Bi-variable: Scatter plot of Liveness vs Instrumentalness
ggplot(spotify_clean, aes(x = instrumentalness, y = liveness)) +
  geom_point(color = "blue") +
  labs(title = "Liveness vs Instrumentalness", x = "Instrumentalness", y = "Liveness") +
  theme_minimal()

# Interpretation:
# Visualizes the relationship between liveness and instrumentalness. Patterns or clustering suggest correlations.

# Multi-variable: Bubble Plot of Duration_ms, Liveness, and Instrumentalness
ggplot(spotify_clean, aes(x = liveness, y = instrumentalness, size = duration_ms)) +
  geom_point(alpha = 0.7, color = "purple") +
  scale_size_continuous(name = "Duration (ms)", range = c(1, 10)) +
  labs(
    title = "Bubble Plot: Liveness, Instrumentalness, and Duration",
    x = "Liveness",
    y = "Instrumentalness"
  ) +
  theme_minimal()


# Interpretation:
# This interactive 3D plot shows how duration, liveness, and instrumentalness interact simultaneously.

### 4. Conclusion Summary ###

# - Descriptive Statistics:
#   - `Liveness` and `Instrumentalness` show their central tendencies and variability, giving insights into their spread in the dataset.
# - Inferential Statistics:
#   - One-sample t-test: Tested if mean liveness significantly deviates from 0.2.
#   - Two-sample t-test: Compared durations for high and low instrumentalness groups.
#   - Paired t-test: Explored differences between liveness and instrumentalness means.
#   - One-way ANOVA: Analyzed differences in duration_ms across keys.
#   - Linear Regression: Examined how liveness predicts duration.
# - Visualizations:
#   - Bar plot: Showed the frequency of tracks across musical keys.
#   - Scatter plot: Explored the relationship between liveness and instrumentalness.
#   - 3D scatter plot: Combined multiple variables to observe their interactions.
# - Overall Insights:
#   - The analysis highlights key attributes of Spotify tracks, such as the impact of liveness and instrumentalness on track duration and the variability across musical keys. 
#   - These insights can guide music production, recommendations, or further research into music analytics.

