a = "Sophus Lie"
b = "house"

idx = []

for i in b:
    s = a.index(i)
    idx.append(s)

print(idx)
print("".join([a[z] for z in idx]))