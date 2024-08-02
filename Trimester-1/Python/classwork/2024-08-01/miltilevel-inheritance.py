class Animal:
    def __init__(self, name):
        self.name = name

    def eat(self):
        print(f"{self.name} is eating.")

class Dog(Animal):
    def bark(self):
        print(f"{self.name} is barking.")

class Bulldog(Dog):
    def guard(self):
        print(f"{self.name} is guarding.")

# Creating an instance of Bulldog
bulldog = Bulldog("Rocky")

# Calling methods from different levels of inheritance
bulldog.eat()
bulldog.bark()
bulldog.guard()