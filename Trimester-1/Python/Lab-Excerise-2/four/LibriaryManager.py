class Book:
    def __init__(self, title, author, publisher, volume, year, isbn):
        self.title = title
        self.author = author
        self.publisher = publisher
        self.volume = volume
        self.year = year
        self.isbn = isbn

    def __str__(self):
        return f"{book.isbn} - {book.title} by {book.author}"

class LibraryManager:
    def __init__(self):
        self.books = {}

    def add_book(self, book):
        self.books[book.isbn] = book

    def remove_book(self, isbn):
        if isbn in self.books:
            del self.books[isbn]

    def get_book_details(self, isbn):
        if isbn in self.books:
            book = self.books[isbn]
            return f"Title: {book.title}\nAuthor: {book.author}\nPublisher: {book.publisher}\nVolume: {book.volume}\nYear: {book.year}\nISBN: {book.isbn}"
        else:
            return "Book not found."

    def search_books(self, keyword):
        found_books = []
        for book in self.books.values():
            if keyword.lower() in book.title.lower() or keyword.lower() in book.author.lower():
                found_books.append(book)
        return found_books

    def list_all_books(self):
        book_list = []
        for book in self.books.values():
            book_list.append(f"Title: {book.title}\nAuthor: {book.author}\nPublisher: {book.publisher}\nVolume: {book.volume}\nYear: {book.year}\nISBN: {book.isbn}\n")
        return book_list

    def update_book_details(self, isbn, new_details):
        if isbn in self.books:
            book = self.books[isbn]
            book.title = new_details.get('title', book.title)
            book.author = new_details.get('author', book.author)
            book.publisher = new_details.get('publisher', book.publisher)
            book.volume = new_details.get('volume', book.volume)
            book.year = new_details.get('year', book.year)

    def check_book_availability(self, isbn):
        return isbn in self.books


if __name__ == "__main__":
    library = LibraryManager()

    while True:
        print(
            """
            --------------------
            MENU

            1. Add book
            2. Remove book
            3. Get book details
            4. Search books
            5. List all books
            6. Update book details
            7. Check book availability
            8. Exit
            --------------------
            """
            )

        choice = input("Enter your choice: ")

        if choice == "1":
            title = input("Enter the title of the book: ")
            author = input("Enter the author of the book: ")
            publisher = input("Enter the publisher of the book: ")
            volume = input("Enter the volume of the book: ")
            year = input("Enter the year of publication: ")
            isbn = input("Enter the ISBN of the book: ")

            book = Book(title, author, publisher, volume, year, isbn)
            library.add_book(book)
            print("Book added successfully.")

        elif choice == "2":
            isbn = input("Enter the ISBN of the book to remove: ")
            library.remove_book(isbn)
            print("Book removed successfully.")

        elif choice == "3":
            isbn = input("Enter the ISBN of the book to get details: ")
            details = library.get_book_details(isbn)
            print(details)

        elif choice == "4":
            keyword = input("Enter the keyword to search books: ")
            found_books = library.search_books(keyword)
            if found_books:
                print("Found books:")
                for book in found_books:
                    print(book.isbn, book.title)
            else:
                print("No books found.")

        elif choice == "5":
            book_list = library.list_all_books()
            if book_list:
                print("All books:")
                for book in book_list:
                    print(book)
            else:
                print("No books in the library.")

        elif choice == "6":
            isbn = input("Enter the ISBN of the book to update details: ")

            if library.check_book_availability(isbn):

                new_title = input("Enter the new title (leave blank to keep existing): ")
                new_author = input("Enter the new author (leave blank to keep existing): ")
                new_publisher = input("Enter the new publisher (leave blank to keep existing): ")
                new_volume = input("Enter the new volume (leave blank to keep existing): ")
                new_year = input("Enter the new year (leave blank to keep existing): ")

                new_details = {}
                if new_title:
                    new_details['title'] = new_title
                if new_author:
                    new_details['author'] = new_author
                if new_publisher:
                    new_details['publisher'] = new_publisher
                if new_volume:
                    new_details['volume'] = new_volume
                if new_year:
                    new_details['year'] = new_year

                library.update_book_details(isbn, new_details)
                print("Book details updated successfully.")

            else:
                print("Book is not available to change.")

        elif choice == "7":
            isbn = input("Enter the ISBN of the book to check availability: ")
            if library.check_book_availability(isbn):
                print("Book is available.")
            else:
                print("Book is not available.")

        elif choice == "8":
            break

        else:
            print("Invalid choice. Please try again.")