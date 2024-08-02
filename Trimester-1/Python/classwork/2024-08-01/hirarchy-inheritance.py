class ParentClass:
    def __init__(self, parent_attr):
        self.parent_attr = parent_attr

    def parent_method(self):
        # Parent class method implementation
        pass


class ChildClass(ParentClass):
    def __init__(self, parent_attr, child_attr):
        super().__init__(parent_attr)
        self.child_attr = child_attr

    def child_method(self):
        # Child class method implementation
        pass


# Create an instance of the child class
child_obj = ChildClass("Parent Attribute", "Child Attribute")

# Access parent class attributes and methods
print(child_obj.parent_attr)
child_obj.parent_method()

# Access child class attributes and methods
print(child_obj.child_attr)
child_obj.child_method()