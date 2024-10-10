import java.util.List;
import java.util.ArrayList;

class NotesApp {
    public static void main(String[] args) {
        System.out.println("Welcome to Notes App");

        List<Note> notes = new ArrayList<>();

        while (true) {
            System.out.println("1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Exit");

            int choice;

            try {
                choice = Integer.parseInt(System.console().readLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice");
                continue;
            }

            if (choice == 1) {
                System.out.println("Enter title: ");
                String title = System.console().readLine();
                System.out.println("Enter content: ");
                String content = System.console().readLine();

                Note note = new Note();
                note.Note(title, content);

                notes.add(note);
            } else if (choice == 2) {
                for (int i = 0; i < notes.size(); i++) {
                    Note note = notes.get(i);
                    note.print();
                }
            } else if (choice == 3) {
                break;
            }
        }

    }
}