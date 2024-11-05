help("barplot")
q = c(1,12,12,1,12,2,3,1,2,1,1,1,12)
tq = table(q)/length(q)
tq
barplot(
  tq, 
  main="something something", 
  names.arg=c('a', 'b', 'c', 'd'), 
  col=c("red", "blue", "yellow", "pink"), 
  ylab="Rel Freq", 
  xlab = "Classes"
  )

