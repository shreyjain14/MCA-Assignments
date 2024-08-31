from abc import ABC, abstractmethod


class Vehicle(ABC):
    @abstractmethod
    def drive(self):
        pass


class Car(Vehicle):
    def drive(self):
        print("Driving a car")

class Motorcycle(Vehicle):
    def drive(self):
        print("Driving a motorcycle")

class Bicycle(Vehicle):
    def drive(self):
        print("Riding a bicycle")


vehicles = [Car(), Motorcycle(), Bicycle()]

for vehicle in vehicles:
    vehicle.drive()