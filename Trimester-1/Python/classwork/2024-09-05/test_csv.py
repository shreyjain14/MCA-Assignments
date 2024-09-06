import pandas as pd

data = pd.concat(pd.read_excel('std_details.xlsx', sheet_name=None, skiprows=3), ignore_index=True)

data = data.sort_values('Marks', ascending=False)
data.to_csv('sorted_data.csv', index=False)

print(data)