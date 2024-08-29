import numpy as np

data = np.array([('apple' , 10), ('banana', 20), ('cherry', 10)], dtype=[('fruit', 'S10'), ('price', float)])

print(data)

data_with_stock = np.array([(fruit, price, 5) for fruit, price in data.tolist()], dtype=[('fruit', 'S10'), ('price', float), ('stock', int)])

print(data_with_stock)

data_without_price = np.array([(fruit, stock) for fruit, price, stock in data_with_stock.tolist()], dtype=[('fruit', 'S10'), ('stock', int)])

print(data_without_price)