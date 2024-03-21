import java.sql.*;
import java.util.Scanner;
import java.sql.Statement;
import static java.sql.DriverManager.getConnection;
public class Service {
    static Scanner inputScanner = new Scanner(System.in);

    public static void fetchLastAddedClientID(Connection conn) {
        String sqlQuery = "SELECT client_id FROM clients ORDER BY client_id DESC LIMIT 1";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {
            if (rs.next()) {
                int clientId = rs.getInt("client_id");
                System.out.println("Last added client ID: " + clientId);
                System.out.println("");
            } else {
                System.out.println("No clients found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fetchLastAddedServiceID(Connection conn) {
        String sqlQuery = "SELECT service_id FROM services ORDER BY service_id DESC LIMIT 1";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {
            if (rs.next()) {
                int serviceId = rs.getInt("service_id");
                System.out.println("Last added service ID: " + serviceId);
            } else {
                System.out.println("No services found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
