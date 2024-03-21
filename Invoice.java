import java.sql.*;
import java.util.Scanner;
import java.sql.Statement;
import static java.sql.DriverManager.getConnection;

public class Invoice {
    static Scanner inputScanner = new Scanner(System.in);

    public static String getMostFrequentService(Connection conn) {
        String mostFrequentService = null;
        String sqlQuery = "SELECT service_name, COUNT(*) AS service_count " +
                          "FROM services " +
                          "GROUP BY service_name " +
                          "ORDER BY service_count DESC " +
                          "LIMIT 1";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {

            if (rs.next()) {
                mostFrequentService = rs.getString("service_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mostFrequentService;
    }

    public static String getClientWithMostOrders(Connection conn) {
        String clientWithMostOrders = null;
        String sqlQuery = "SELECT c.client_id, c.client_name, COUNT(ii.service_id) AS order_count " +
                          "FROM clients c " +
                          "LEFT JOIN invoice_items ii ON c.client_id = ii.client_id " +
                          "GROUP BY c.client_id, c.client_name " +
                          "ORDER BY order_count DESC " +
                          "LIMIT 1";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {

            if (rs.next()) {
                int clientId = rs.getInt("client_id");
                String clientName = rs.getString("client_name");
                clientWithMostOrders = clientName + " - Client ID: "+ clientId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientWithMostOrders;
    }

    public static int calculateWeeklyRevenue(Connection conn) {
        int weeklyRevenue = 0;
        String sqlQuery = "SELECT SUM(subtotal) AS totalRevenue " +
                          "FROM invoice_items " +
                          "WHERE date >= DATE_SUB(CURDATE(), INTERVAL 1 WEEK)";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {

            if (rs.next()) {
                weeklyRevenue = rs.getInt("totalRevenue");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return weeklyRevenue;
    }
}
