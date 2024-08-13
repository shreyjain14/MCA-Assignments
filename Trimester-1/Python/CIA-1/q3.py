class LibraryItem:
    def get_details(self):
        pass


class Magazine(LibraryItem):
    def __init__(self, issue_number):
        self.issue_number = issue_number

    def get_details(self):
        return f"Issue number: {self.issue_number}"


class DVD(LibraryItem):
    def __init__(self, duration):
        self.duration = duration

    def get_details(self):
        return f"Duration: {self.duration}"
    

magazine1 = Magazine(1)
dvd1 = DVD(120)

print(magazine1.get_details())
print(dvd1.get_details())