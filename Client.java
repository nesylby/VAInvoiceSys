import java.sql.*;
import java.util.Scanner;
import java.sql.Statement;
import static java.sql.DriverManager.getConnection;

public class Client {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/invoicesys";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "4G0NF4DEJDJ";
 
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            displayMenu();
            int userChoice;
            do {
                userChoice = inputScanner.nextInt();
                switch (userChoice) {
                    case 1:
                        viewClients(conn);
                        break;
                    case 2:
                        addClient(conn);
                        break;
                    case 3:
                        deleteClient(conn);
                        break;
                    case 4:
                        updateClient(conn);
                        break;
                    case 0:
                        endApplication();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } while (userChoice != 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayMenu() {
        System.out.println("=== Client Management System ===");
        System.out.println("1. View Clients");
        System.out.println("2. Add Client");
        System.out.println("3. Delete Client");
        System.out.println("4. Update Client");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void viewClients(Connection conn) throws SQLException {
        System.out.println("=== View Clients ===");
        String sql = "SELECT * FROM clients";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int clientId = rs.getInt("client_id");
                String clientName = rs.getString("client_name");
                String contactInfo = rs.getString("contact_information");
                String billingAddress = rs.getString("billing_address");
                double totalAmountBilled = rs.getDouble("total_amount_billed");

                System.out.println("Client ID: " + clientId);
                System.out.println("Client Name: " + clientName);
                System.out.println("Contact Information: " + contactInfo);
                System.out.println("Billing Address: " + billingAddress);
                System.out.println("Total Amount Billed: " + totalAmountBilled);
                System.out.println("-------------------");
            }
        }
    }

    private static void addClient(Connection conn) throws SQLException {
        System.out.println("=== Add Client ===");
        // Gather input for new client
        // For simplicity, assume proper input handling and validation
        String clientName = "New Client Name";
        String contactInfo = "New Contact Info";
        String billingAddress = "New Billing Address";
        double totalAmountBilled = 0.0;

        String sql = "INSERT INTO clients (client_name, contact_information, billing_address, total_amount_billed) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, clientName);
            pstmt.setString(2, contactInfo);
            pstmt.setString(3, billingAddress);
            pstmt.setDouble(4, totalAmountBilled);
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new client has been added.");
            } else {
                System.out.println("Failed to add a new client.");
            }
        }
    }

    private static void deleteClient(Connection conn) throws SQLException {
        System.out.println("=== Delete Client ===");
        System.out.print("Enter Client ID to delete: ");
        int clientIdToDelete = inputScanner.nextInt();

        String sql = "DELETE FROM clients WHERE client_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, clientIdToDelete);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Client with ID " + clientIdToDelete + " has been deleted.");
            } else {
                System.out.println("No client found with ID " + clientIdToDelete);
            }
        }
    }

    private static void updateClient(Connection conn) throws SQLException {
        System.out.println("=== Update Client ===");
        System.out.print("Enter Client ID to update: ");
        int clientIdToUpdate = inputScanner.nextInt();

        // For simplicity, let's assume we are updating the client name only
        System.out.print("Enter new Client Name: ");
        String newClientName = inputScanner.next();

        String sql = "UPDATE clients SET client_name = ? WHERE client_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newClientName);
            pstmt.setInt(2, clientIdToUpdate);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Client with ID " + clientIdToUpdate + " has been updated.");
            } else {
                System.out.println("No client found with ID " + clientIdToUpdate);
            }
        }
    }

    private static void endApplication() {
        System.out.println("Exiting Client Management System. Goodbye!");
        System.exit(0);
    }

    // Assume you have an input scanner for user input
    private static final Scanner inputScanner = new Scanner(System.in);

}
