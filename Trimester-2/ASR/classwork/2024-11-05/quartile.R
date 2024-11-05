h = c(123,123,123,123,123,132,123,112,111,112,123,123,154,161,154,176,187,134,178,116,175,193)

quantile(h)

# decile
quantile(h, probs = seq(0,1,0.1))

# percentile
quantile(h, probs = seq(0,1,0.01))
