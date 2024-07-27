class PremiumCar:
    
    def __init__(self, make, model, year, distance):
        self.make = make
        self.model = model
        self.year = year
        self.distance = distance

    def __str__(self):
        return self.model
    
    def add_distance(self, distance):
        self.distance += distance
        print(f"car has been driven {self.distance} kms.")
