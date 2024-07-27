weather_data = [
    {"date": "2024-08-01", "max_temp": 28, "min_temp": 20, "humidity": 75},
    {"date": "2024-08-02", "max_temp": 30, "min_temp": 22, "humidity": 80},
    {"date": "2024-08-03", "max_temp": 32, "min_temp": 24, "humidity": 85},
    {"date": "2024-08-04", "max_temp": 29, "min_temp": 21, "humidity": 70},
    {"date": "2024-08-05", "max_temp": 31, "min_temp": 23, "humidity": 75},
    {"date": "2024-08-06", "max_temp": 33, "min_temp": 25, "humidity": 80},
    {"date": "2024-08-07", "max_temp": 30, "min_temp": 22, "humidity": 75},
    {"date": "2024-08-08", "max_temp": 28, "min_temp": 20, "humidity": 70},
    {"date": "2024-08-09", "max_temp": 27, "min_temp": 19, "humidity": 65},
    {"date": "2024-08-10", "max_temp": 26, "min_temp": 18, "humidity": 60}
]

def find_highest_and_lowest_temperatures():
    highest_temp = float('-inf')
    lowest_temp = float('inf')
    for day in weather_data:
        max_temp = day["max_temp"]
        min_temp = day["min_temp"]
        if max_temp > highest_temp:
            highest_temp = max_temp
        if min_temp < lowest_temp:
            lowest_temp = min_temp
    return highest_temp, lowest_temp

def compute_average_humidity(start_date, end_date):
    total_humidity = 0
    days = 0
    for day in weather_data:
        if day["date"][8:] < start_date or day["date"][8:] > end_date:
            humidity = day["humidity"]
            total_humidity += humidity
            days += 1
    average_humidity = total_humidity / days
    return average_humidity

while True:
    print(
        """
        -------------------------
        Menu:
          1. Find highest and lowest temperatures
          2. Compute average humidity
          3. Exit
        -------------------------
        """
          )

    choice = input("Enter your choice (1-3): ")

    if choice == "1":
        highest_temp, lowest_temp = find_highest_and_lowest_temperatures()
        print("Highest temperature:", highest_temp)
        print("Lowest temperature:", lowest_temp)
    elif choice == "2":
        start_date = input("Enter start date only for August 2024(DD): ")
        end_date = input("Enter end date only for August 2024 (DD): ")

        average_humidity = compute_average_humidity(start_date, end_date)
        print("Average humidity:", average_humidity)
    elif choice == "3":
        print("Exiting...")
        break
    else:
        print("Invalid choice. Please try again.")
