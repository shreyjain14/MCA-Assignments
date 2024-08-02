class Parent:
    def __init__(self):
        self.parent_property = "Parent Property"
    
    def parent_method(self):
        print("Parent Method")

class Child(Parent):
    def __init__(self):
        super().__init__()
        self.child_property = "Child Property"
    
    def child_method(self):
        print("Child Method")

# Creating an object of Child class
child_obj = Child()

# Accessing properties and methods of both Parent and Child class
print(child_obj.parent_property)
child_obj.parent_method()
print(child_obj.child_property)
print(child_obj.parent_property)
child_obj.child_method()