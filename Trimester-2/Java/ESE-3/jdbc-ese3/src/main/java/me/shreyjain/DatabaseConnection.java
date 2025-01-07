package me.shreyjain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnection {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Connected to the database!");

                Scanner scanner = new Scanner(System.in);

                while (true) { 
                    System.out.println(
                        "Student System\n1. Insert\n2. Select\n3.Search\n4. Update\n5.Delete"
                    )

                    int option = scanner.nextInt();

                    switch (option) {

                        case 1:
                            break;

                        case 2:
                            break;

                        case 3:
                            break;

                        case 4:
                            break;

                        case 5:
                            break;

                        default:
                            System.out.println("Enter a valid option");

                    }

                }


            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addStudent() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Name:");
        String name = scanner.nextLine();
        System.out.println("ID:");
        String id = scanner.nextLine();
        System.out.println("Couse:");
        String course = scanner.nextLine();
        System.out.println("Date:");
        String date = scanner.nextLine();
        System.out.println("Attandance (T/F):");
        Boolean attendance;
        if (Objects.equals(scanner.nextLine(), "T")) {
            attendance = true;
        };
        
        PreparedStatement pstmt = Connection.PreparedStatement("INSERT INTO Students ());

    }
}