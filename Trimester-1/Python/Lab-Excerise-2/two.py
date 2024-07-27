def add(s, num):
    s.add(num)
    return s

def remove(s, num): 
    s.remove(num)
    return s

def union_and_intersecrion(s1, s2):
    return s1.union(s2), s1.intersection(s2)

def difference(s1, s2):
    return s1.difference(s2)

def check_subset(s1, s2):
    return s1.issubset(s2)

def symmetric_difference(s1, s2):
    return s1.symmetric_difference(s2)

def power_set(a):
    s = list(a)
    ret = []
    for i in range(1 << len(s)):
        x = set()
        for j in range(len(s)):
            if i & (1 << j):
                x.add(s[j])
    ret.append(x) 
    return ret

def unique_subsets(a):
    s = list(a)
    ret = []
    for i in range(1 << len(s)):
        x = set()
        for j in range(len(s)):
            if i & (1 << j):
                x.add(s[j])
        if x not in ret:
            ret.append(x) 
    return ret



if __name__ == '__main__':
    s1 = set()
    s2 = set()

    while True:
        print(
            """
            --------------------
            MENU\n
            1. Change set1
            2. Change set2
            3. Add element
            4. Remove element
            5. Union and intersection
            6. Difference
            7. Check subset
            8. Symmetric difference
            9. Power set
            10. Unique subsets
            11. Quit
            --------------------\n
            """
            )

        try:
            choice = int(input("Enter option: "))
        except ValueError:
            print("Invalid option")
            continue

        match choice:
            case 1:
                s1_len = int(input("Enter the length of the first set: "))
                for i in range(s1_len):
                    s1.add(int(input(f"Enter element {i+1}: ")))
            case 2:
                s2_len = int(input("Enter the length of the second set: "))
                for i in range(s2_len):
                    s2.add(int(input(f"Enter element {i+1}: ")))
            case 3:
                num = int(input("Enter the element to add: "))
                print("add element: ", add(s1, num))
            case 4:
                num = int(input("Enter the element to remove: "))
                print("remove element: ", remove(s1, num))
            case 5:
                print("union and intersection: ", union_and_intersecrion(s1, s2))
            case 6:
                print("difference: ", difference(s1, s2))
            case 7:
                print("check subset: ", check_subset(s1, s2))
            case 8:
                print("symmetric differnce: ", symmetric_difference(s1, s2))
            case 9:
                print("powerset", power_set(s1))
            case 10:
                print("unique subset", unique_subsets(s1))
            case 11:
                break
            case _:
                print("Invalid option")

