import module_ListFunction

def print_menu():
    print("1. Calculate sum")
    print("2. Find maximum")
    print("3. Find minimum")
    print("4. Calculate average")
    print("5. Calculate median")
    print("0. Exit")

def get_choice():
    choice = input("Enter your choice: ")
    return choice

def get_list():
    elements = input("Enter the elements of the list (separated by spaces): ")
    a = [int(x) for x in elements.split()]
    return a

a = get_list() 

while True:
    print_menu()
    choice = get_choice()

    if choice == "1":
        print("Sum:", module_ListFunction.l_sum(a))
    elif choice == "2":
        print("Maximum:", module_ListFunction.maximum(a))
    elif choice == "3":
        print("Minimum:", module_ListFunction.minimum(a))
    elif choice == "4":
        print("Average:", module_ListFunction.avg(a))
    elif choice == "5":
        print("Median:", module_ListFunction.median(a))
    elif choice == "0":
        print("Exiting...")
        break
    else:
        print("Invalid choice. Please try again.")