class LibraryItem:
    pass

class Book(LibraryItem):
    def __init__(self, author, genre, isbn, title):
        self.author = author
        self.genre = genre
        self.isbn = isbn
        self.title = title
        self.availabe = True

    def checkout(self):
        if self.availabe:
            self.availabe = False
            return f"{self.title} checked out"
        return f"{self.title} is not available"

    def returnBook(self):
        if not self.availabe:
            self.availabe = True
            return f"{self.title} returned"
        return f"{self.title} is already available"
    
    def updateAvailability(self, status):
        if self.availabe:
            self.availabe = status
            return f"Changed availability of {self.title} to {status}"
        return "Book is currently checked out."

    def getDetails(self):
        return f"""\
Isbn: {self.isbn}
Title: {self.title}
Author: {self.author}
Genre: {self.genre}
"""
    
    def addToLibrary(self):
        return f"Book added to library: {self.title}"


book1 = Book('Shrey', 'Sci-Fi', 0, 'The book of science')
book2 = Book('Dave', 'Fiction', 1, 'The book of fiction')

print(book1.returnBook())
print(book1.checkout())
print(book1.checkout())
print(book1.updateAvailability(False))
print(book1.returnBook())
print(book1.updateAvailability(True))
print(book1.updateAvailability(False))
print(book1.addToLibrary())
print(book1.getDetails())

print(book2.returnBook())
print(book2.checkout())
print(book2.checkout())
print(book2.updateAvailability(False))
print(book2.returnBook())
print(book2.updateAvailability(True))
print(book2.updateAvailability(False))
print(book2.addToLibrary())
print(book2.getDetails())
