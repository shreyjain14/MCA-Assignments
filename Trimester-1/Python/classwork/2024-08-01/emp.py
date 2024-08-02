class Employee:
    def __init__(self, name, salary, position, id):
        self.name = name
        self.salary = salary
        self.position = position
        self.id = id

    def display(self):
        print(f"Name: {self.name}")
        print(f"Salary: {self.salary}")
        print(f"Position: {self.position}")
        print(f"ID: {self.id}")

    def increment(self, amount):
        self.salary += amount
        print(f"Salary after increment: {self.salary}")

    def change_position(self, new_position):
        self.position = new_position
        print(f"Position after change: {self.position}")


e1 = Employee("Ram", 4025, "Ass. Prof.", 1)

e1.display
e1.increment(250)
e1.change_position("Professor")
e1.display
