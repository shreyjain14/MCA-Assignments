import java.util.*;

class Member {
    Integer id;
    String name;
    List<Book> borrowedBooks;

    public Member(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.borrowedBooks = new ArrayList<Book>();
    }

    public void borrowBook(Book book) {
        if (book.isAvailable) {
            borrowedBooks.add(book);
            book.isAvailable = false;
        } else {
            System.out.println("Book is not available");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            borrowedBooks.remove(book);
            book.isAvailable = true;
        } else {
            System.out.println("Book is not borrowed by this member");
        }
    }

    public void printBorrowedBooks() {
        for (int i = 0; i < borrowedBooks.size(); i++) {
            Book book = borrowedBooks.get(i);
            System.out.println("Title: " + book.title);
            System.out.println("Author: " + book.author);
            System.out.println("BISN: " + book.bisn);
            System.out.println("---------------------");
        }
    }

}