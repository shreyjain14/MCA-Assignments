class Student:
    def __init__(self, name, age, branch):
        self.name = name
        self.age = age
        self.branch = branch

    def __str__(self) -> str:
        return self.name
    
    def read(self):
        print([self.name, self.age, self.branch])

    def write(self, name, age, branch):
        self.name = name
        self.age = age
        self.branch = branch

S1 = Student('shrey', 20, 'MCA')
S2 = Student('anjaney', 21, 'MCA')

print(S1, S2)
S1.read()