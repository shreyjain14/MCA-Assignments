package me.shreyjain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public static void insertAttendance(Connection conn, Scanner scanner) {
        try {
            System.out.println("Enter Student ID:");
            int studentId = scanner.nextInt();
            scanner.nextLine(); // consume newline

            System.out.println("Enter Student Name:");
            String studentName = scanner.nextLine();

            System.out.println("Enter Course:");
            String course = scanner.nextLine();

            System.out.println("Enter Date (YYYY-MM-DD):");
            String date = scanner.nextLine();

            System.out.println("Enter Attendance Status (Present/Absent):");
            String attendanceStatus = scanner.nextLine();

            String query = "INSERT INTO attendance (student_id, student_name, course, date, attendance_status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, studentId);
            stmt.setString(2, studentName);
            stmt.setString(3, course);
            stmt.setString(4, date);
            stmt.setString(5, attendanceStatus);

            stmt.executeUpdate();
            System.out.println("Attendance record inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewAllAttendance(Connection conn) {
        try {
            String query = "SELECT * FROM attendance";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Student ID: " + rs.getInt("student_id") +
                                   ", Name: " + rs.getString("student_name") +
                                   ", Course: " + rs.getString("course") +
                                   ", Date: " + rs.getString("date") +
                                   ", Attendance Status: " + rs.getString("attendance_status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void searchAttendance(Connection conn, Scanner scanner) {
        try {
            System.out.println("Enter Student ID:");
            int studentId = scanner.nextInt();
            scanner.nextLine(); // consume newline

            String query = "SELECT * FROM attendance WHERE student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Student ID: " + rs.getInt("student_id") +
                                   ", Name: " + rs.getString("student_name") +
                                   ", Course: " + rs.getString("course") +
                                   ", Date: " + rs.getString("date") +
                                   ", Attendance Status: " + rs.getString("attendance_status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateAttendanceStatus(Connection conn, Scanner scanner) {
        try {
            System.out.println("Enter Student ID:");
            int studentId = scanner.nextInt();
            scanner.nextLine(); // consume newline

            System.out.println("Enter Date (YYYY-MM-DD):");
            String date = scanner.nextLine();

            System.out.println("Enter New Attendance Status (Present/Absent):");
            String newStatus = scanner.nextLine();

            String query = "UPDATE attendance SET attendance_status = ? WHERE student_id = ? AND date = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, newStatus);
            stmt.setInt(2, studentId);
            stmt.setString(3, date);

            stmt.executeUpdate();
            System.out.println("Attendance status updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAttendance(Connection conn, Scanner scanner) {
        try {
            System.out.println("Enter Student ID:");
            int studentId = scanner.nextInt();
            scanner.nextLine(); // consume newline

            System.out.println("Enter Date (YYYY-MM-DD):");
            String date = scanner.nextLine();

            String query = "DELETE FROM attendance WHERE student_id = ? AND date = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, studentId);
            stmt.setString(2, date);

            stmt.executeUpdate();
            System.out.println("Attendance record deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection(); Scanner scanner = new Scanner(System.in)) {
            if (connection != null) {
                System.out.println("Connected to the database!");

                while (true) {
                    System.out.println("\nStudent System\n1. Insert\n2. Select\n3. Search\n4. Update\n5. Delete\n6. Exit");
                    System.out.print("Enter your option: ");
                    int option = scanner.nextInt();

                    switch (option) {
                        case 1:
                            insertAttendance(connection, scanner);
                            break;

                        case 2:
                            viewAllAttendance(connection);
                            break;

                        case 3:
                            searchAttendance(connection, scanner);
                            break;

                        case 4:
                            updateAttendanceStatus(connection, scanner);
                            break;

                        case 5:
                            deleteAttendance(connection, scanner);
                            break;

                        case 6:
                            System.out.println("Exiting...");
                            System.exit(0);

                        default:
                            System.out.println("Enter a valid option.");
                    }
                }
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
