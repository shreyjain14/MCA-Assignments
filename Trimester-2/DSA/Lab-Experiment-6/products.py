import random

file_path = 'products.txt'

ids = list(range(1, 100001))
random.shuffle(ids)

products = [f"{id} {random.uniform(5.00, 20.00):.2f} Product_{id}" for id in ids]

with open(file_path, 'w') as file:
    for product in products:
        file.write(product + '\n')
