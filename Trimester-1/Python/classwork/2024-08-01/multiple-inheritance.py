class Animal:
    def __init__(self, name):
        self.name = name

    def speak(self):
        pass

class Mammal(Animal):
    def speak(self):
        return "Mammal: Roar!"

class Bird(Animal):
    def speak(self):
        return "Bird: Chirp!"

# The first class listed in the parentheses is the one whose method is called 
# if there are multiple methods with the same name
class Platypus(Bird, Mammal):
    pass

# Create an instance of Platypus
perry = Platypus("Perry")

# Call the speak() method
print(perry.speak())