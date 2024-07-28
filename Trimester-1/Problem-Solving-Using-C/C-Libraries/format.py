import pandas as pd


def get_data():

    df = pd.read_csv('fns.csv')

    result = {}

    for index, row in df.iterrows():
        system_file = row['System Include File']
        function_dict = {
            "name": row['Function'],
            "syntax": row['Function Prototype'],
            "desc": row['Description']
        }
        if system_file not in result:
            result[system_file] = []
        result[system_file].append(function_dict)

    return result

