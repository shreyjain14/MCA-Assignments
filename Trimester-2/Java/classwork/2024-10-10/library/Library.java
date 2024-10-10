import java.util.*;

class Library {

    List<Book> books = new ArrayList<>();
    List<Member> members = new ArrayList<>();

    public static void main(String args[]) {

        Library library = new Library();

        while (true) {

            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Print Books");
            System.out.println("4. Print Members");
            System.out.println("5. Check Out Book");
            System.out.println("6. Return Book");
            System.out.println("7. Exit");

            int choice;

            try {
                choice = Integer.parseInt(System.console().readLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice");
                continue;
            }

            if (choice == 1) {

                System.out.println("Enter ISBN: ");
                String isbn = System.console().readLine();
                System.out.println("Enter Title: ");
                String title = System.console().readLine();
                System.out.println("Enter Author: ");
                String author = System.console().readLine();

                library.addBook(isbn, title, author);

            } else if (choice == 2) {

                System.out.println("Enter ID: ");
                Integer id = Integer.parseInt(System.console().readLine());
                System.out.println("Enter Name: ");
                String name = System.console().readLine();

                library.addMember(id, name);

            } else if (choice == 3) {

                library.printBooks();

            } else if (choice == 4) {

                library.printMembers();

            } else if (choice == 5) {

                System.out.println("Enter Member ID: ");
                Integer memberId = Integer.parseInt(System.console().readLine());
                System.out.println("Enter ISBN: ");
                String isbn = System.console().readLine();

                library.checkOutBook(memberId, isbn);

            } else if (choice == 6) {

                System.out.println("Enter Member ID: ");
                Integer memberId = Integer.parseInt(System.console().readLine());
                System.out.println("Enter ISBN: ");
                String isbn = System.console().readLine();

                library.returnBook(memberId, isbn);

            } else if (choice == 7) {

                break;

            } else {

                System.out.println("Invalid choice");

            }

        }

    }

    public void addBook(String isbn, String title, String author) {
        Book book = new Book(isbn, title, author);
        books.add(book);
    }

    public void addMember(Integer id, String name) {
        Member member = new Member(id, name);
        members.add(member);
    }

    public void printBooks() {
        System.out.println("---------------------");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            book.print();
        }
    }

    public void printMembers() {
        System.out.println("---------------------");
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            System.out.println("ID: " + member.id);
            System.out.println("Name: " + member.name);
            System.out.println("---------------------");
        }
    }

    public void checkOutBook(Integer memberId, String isbn) {
        Member member = members.get(memberId);
        Book book = null;
        for (Book b : books) {
            if (b.isbn.equals(isbn)) {
                book = b;
                break;
            }
        }

        member.borrowBook(book);
    }

    public void returnBook(Integer memberId, String isbn) {
        Member member = members.get(memberId);
        Book book = null;
        for (Book b : books) {
            if (b.isbn.equals(isbn)) {
                book = b;
                break;
            }
        }

        member.returnBook(book);
    }

}