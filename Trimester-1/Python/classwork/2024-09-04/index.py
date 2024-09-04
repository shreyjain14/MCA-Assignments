import pandas as pd

data = {
    'Name': ['John', 'Anna', 'Peter', 'Linda'],
    'Age': [25, 30, 35, 40]
}

df = pd.DataFrame(data)
print(df)
print(df['Name'])
print(df['Age'])