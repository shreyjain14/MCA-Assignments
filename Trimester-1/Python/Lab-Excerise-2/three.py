def merge_dict(*args):
    result = {}
    for dictionary in args:
        result.update(dictionary)
    return result

def common_keys(*args):
    result = set(args[0].keys())
    for dictionary in args[1:]:
        result = result.intersection(dictionary.keys())
    return result

def invert_dict(dictionary):
    ret = {}

    for key, value in dictionary.items():
        if value not in ret:
            ret[value] = [key]
        else:
            ret[value] = ret[value] + [key]

    return ret

def find_common_key_value_pairs(*args):
    result = set(args[0].items())
    for dictionary in args[1:]:
        result = result.intersection(dictionary.items())
    return result


if __name__ == "__main__":
    a = {'a': 1, 'g': 1, 'b': 2}
    b = {'b': 2, 'c': 4}

    x = [a, b]

    while True:

        print(
            """
            --------------------
            MENU

            1. Merge dictionaries
            2. Find common keys
            3. Invert dictionary (a)
            4. Find common Key value pairs
            5. Exit
            --------------------
            """
            )
        
        choice = input("Enter your choice: ")

        match choice:
            case "1":
                print("merged dicts: ", merge_dict(a,b))

            case "2":
                print("common keys: ", common_keys(a,b))

            case "3":
                print("inverted dict: ", invert_dict(a))

            case "4":
                print("common key value pairs: ", find_common_key_value_pairs(a,b))

            case "5":
                break

            case _:
                print("Invalid choice")