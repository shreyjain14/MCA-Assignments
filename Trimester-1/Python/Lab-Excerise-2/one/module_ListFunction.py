def maximum(a):
    return max(a)

def minimum(a):
    return min(a)

def l_sum(a):
    return sum(a)

def avg(a):
    return sum(a)/len(a)

def median(a):
    a.sort()
    if len(a) % 2 == 0:
        return (a[len(a)//2] + a[len(a)//2-1])/2
    else:
        return a[len(a)//2]
    
