a <- c(1,2,3,4,5,2,23,41,341,4,134,12,31,1,3,3,4,1,23,134,3,4,122,1,2,3,12,3)

af = table(a)

rf = af/length(a)

print("Abosolute Frequency")
print(af)

print("Relative frequency")
print(rf)

ra = range(a)

print("Range")
print(ra)

breaks = seq(10,50,by=10)
print(breaks)

ac = cut(a, breaks, right=FALSE)
act = table(ac)

print(act)
print(cbind(act))

acrf = cbind(table(ac)/length(ac))
print(acrf)

accf = cbind(cumsum(table(ac)/length(ac)))
print(accf)
