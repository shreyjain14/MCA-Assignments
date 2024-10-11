x = c(1,2,3,4,5,6,7,8,9)

# seq
# range fn
seq(from=3, to=5)
seq(from=3, length=5)
seq(from=3, length=5, by=0.5)
seq(from=3, by=0.5, length=3)

# paste
# adds 2 things together with a space
paste("xyz", 1:10)
paste("xyz", 1:10, sep='/')

# rep
rep(c(3,4,5), 3)
rep(1:10, times=3)
rep(x, each=3)
rep(x, each=3, times=3)

# positional and value
which (x==5)
