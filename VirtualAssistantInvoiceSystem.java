import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class VirtualAssistantInvoiceSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Connection connection;

    public static void main(String[] args) {
        connectToDatabase();

        while (true) {
            System.out.println("\n=== Virtual Assistant Invoice System ===");
            System.out.println("1. Client Management");
            System.out.println("2. Service Management");
            System.out.println("3. Invoice Management");
            System.out.println("4. Analytics");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    manageClients();
                    break;
                case 2:
                    manageServices();
                    break;
                case 3:
                    manageInvoices();
                    break;
                case 4:
                    showAnalytics();
                    break;
                case 5:
                    System.out.println("\nExiting the system. Goodbye!");
                    closeDatabaseConnection();
                    System.exit(0);
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }

    private static void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/VAInvoiceSystem";
            String username = "root";
            String password = "4G0NF4DEJDJ";
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database.");
        } catch (Exception e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            System.exit(0);
        }
    }

    private static void closeDatabaseConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void manageClients() {
        System.out.println("\n===  Client Management Menu:  ===");
        System.out.println("1. Add Client");
        System.out.println("2. View Clients");
        System.out.println("3. Update Client");
        System.out.println("4. Delete Client");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addClient();
                break;
            case 2:
                viewClients();
                break;
            case 3:
                updateClient();
                break;
            case 4:
                deleteClient();
                break;
            default:
                System.out.println("\nInvalid choice. Please try again.");
        }
    }

    private static void addClient() {
        System.out.println("\nEnter client information:");
        System.out.print("Full Name: ");
        String fullName = scanner.nextLine();
        System.out.print("Email Address: ");
        String emailAddress = scanner.nextLine();
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Billing Address: ");
        String billingAddress = scanner.nextLine();
        System.out.print("Payment Terms (e.g., Net 30): ");
        String paymentTerms = scanner.nextLine();
        System.out.print("Type of Service Required: ");
        String serviceType = scanner.nextLine();
        System.out.print("Frequency of Service (e.g., Monthly, Weekly): ");
        String serviceFrequency = scanner.nextLine();

        String sql = "INSERT INTO clients (full_name, email_address, phone_number, billing_address, payment_terms, service_type, service_frequency) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, fullName);
            statement.setString(2, emailAddress);
            statement.setString(3, phoneNumber);
            statement.setString(4, billingAddress);
            statement.setString(5, paymentTerms);
            statement.setString(6, serviceType);
            statement.setString(7, serviceFrequency);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("\nClient added successfully!");
            } else {
                System.out.println("\nFailed to add client.");
            }
        } catch (SQLException e) {
            System.out.println("\nError adding client: " + e.getMessage());
        }
    }

    private static void viewClients() {
        String sql = "SELECT * FROM clients";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("\nList of Clients:");
            while (resultSet.next()) {
                String fullName = resultSet.getString("full_name");
                String emailAddress = resultSet.getString("email_address");
                String phoneNumber = resultSet.getString("phone_number");
                String billingAddress = resultSet.getString("billing_address");
                String paymentTerms = resultSet.getString("payment_terms");
                String serviceType = resultSet.getString("service_type");
                String serviceFrequency = resultSet.getString("service_frequency");

                System.out.println("Full Name: " + fullName);
                System.out.println("Email Address: " + emailAddress);
                System.out.println("Phone Number: " + phoneNumber);
                System.out.println("Billing Address: " + billingAddress);
                System.out.println("Payment Terms: " + paymentTerms);
                System.out.println("Service Type: " + serviceType);
                System.out.println("Service Frequency: " + serviceFrequency);
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("\nError viewing clients: " + e.getMessage());
        }
    }

    private static void updateClient() {
        System.out.print("\nEnter client email address to update: ");
        String email = scanner.nextLine();
        System.out.print("Enter new Full Name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter new Phone Number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter new Billing Address: ");
        String billingAddress = scanner.nextLine();
        System.out.print("Enter new Payment Terms: ");
        String paymentTerms = scanner.nextLine();
        System.out.print("Enter new Type of Service Required: ");
        String serviceType = scanner.nextLine();
        System.out.print("Enter new Frequency of Service: ");
        String serviceFrequency = scanner.nextLine();

        String sql = "UPDATE clients SET full_name = ?, phone_number = ?, billing_address = ?, payment_terms = ?, service_type = ?, service_frequency = ? " +
                     "WHERE email_address = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, fullName);
            statement.setString(2, phoneNumber);
            statement.setString(3, billingAddress);
            statement.setString(4, paymentTerms);
            statement.setString(5, serviceType);
            statement.setString(6, serviceFrequency);
            statement.setString(7, email);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("\nClient updated successfully!");
            } else {
                System.out.println("\nClient not found with the given email address.");
            }
        } catch (SQLException e) {
            System.out.println("\n Error updating client: " + e.getMessage());
        }
        }
        private static void deleteClient() {
            System.out.print("\nEnter client email address to delete: ");
            String email = scanner.nextLine();
        
            String sql = "DELETE FROM clients WHERE email_address = ?";
        
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
        
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("\nClient deleted successfully!");
                } else {
                    System.out.println("\nClient not found with the given email address.");
                }
            } catch (SQLException e) {
                System.out.println("\nError deleting client: " + e.getMessage());
            }
        }
        
        private static void manageServices() {
            System.out.println("\n===  Service Management Menu:  ===");
            System.out.println("1. Add Service");
            System.out.println("2. View Services");
            System.out.println("3. Update Service");
            System.out.println("4. Delete Service");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
        
            switch (choice) {
                case 1:
                    addService();
                    break;
                case 2:
                    viewServices();
                    break;
                case 3:
                    updateService();
                    break;
                case 4:
                    deleteService();
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
        
        private static void addService() {
            System.out.println("\nEnter service information:");
            System.out.print("Service Name: ");
            String serviceName = scanner.nextLine();
            System.out.print("Hourly Rate: ");
            double hourlyRate = scanner.nextDouble();
            scanner.nextLine();
        
            String sql = "INSERT INTO services (service_name, hourly_rate) VALUES (?, ?)";
        
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, serviceName);
                statement.setDouble(2, hourlyRate);
        
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("\nService added successfully!");
                } else {
                    System.out.println("\nFailed to add service.");
                }
            } catch (SQLException e) {
                System.out.println("\nError adding service: " + e.getMessage());
            }
        }
        
        private static void viewServices() {
            String sql = "SELECT * FROM services";
        
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
        
                System.out.println("\nList of Services:");
                while (resultSet.next()) {
                    String serviceName = resultSet.getString("service_name");
                    double hourlyRate = resultSet.getDouble("hourly_rate");
        
                    System.out.println("Service Name: " + serviceName);
                    System.out.println("Hourly Rate: " + hourlyRate);
                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println("\nError viewing services: " + e.getMessage());
            }
        }
        
        private static void updateService() {
            System.out.print("\nEnter service name to update: ");
            String serviceName = scanner.nextLine();
            System.out.print("Enter new Service Name: ");
            String newServiceName = scanner.nextLine();
            System.out.print("Enter new Hourly Rate: ");
            double newHourlyRate = scanner.nextDouble();
            scanner.nextLine();
        
            String sql = "UPDATE services SET service_name = ?, hourly_rate = ? WHERE service_name = ?";
        
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, newServiceName);
                statement.setDouble(2, newHourlyRate);
                statement.setString(3, serviceName);
        
                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("\nService updated successfully!");
                } else {
                    System.out.println("\nService not found with the given name.");
                }
            } catch (SQLException e) {
                System.out.println("\nError updating service: " + e.getMessage());
            }
        }
        
        private static void deleteService() {
            System.out.print("\nEnter service name to delete: ");
            String serviceName = scanner.nextLine();
        
            String sql = "DELETE FROM services WHERE service_name = ?";
        
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, serviceName);
        
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("\nService deleted successfully!");
                } else {
                    System.out.println("\nService not found with the given name.");
                }
            } catch (SQLException e) {
                System.out.println("\nError deleting service: " + e.getMessage());
            }
        }
        
        private static void manageInvoices() {
            System.out.println("\n===  Invoice Management Menu:  ===");
            System.out.println("1. Generate Invoice");
            System.out.println("2. View Invoices");
            System.out.println("3. Mark Invoice as Paid");
            System.out.println("4. Delete Invoice");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
        
            switch (choice) {
                case 1:
                    generateInvoice();
                    break;
                case 2:
                    viewInvoices();
                    break;
                case 3:
                    markInvoiceAsPaid();
                    break;
                case 4:
                    deleteInvoice();
                    break;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
        
        private static void viewInvoices() {
            String sql = "SELECT * FROM invoices";
        
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {
        
                System.out.println("\nList of Invoices:");
                while (resultSet.next()) {
                    int invoiceNumber = resultSet.getInt("invoice_number");
                    String clientName = resultSet.getString("client_name");
                    LocalDate invoiceDate = resultSet.getDate("invoice_date").toLocalDate();
                    LocalDate dueDate = resultSet.getDate("due_date").toLocalDate();
                    String serviceName = resultSet.getString("service_name");
                    double numberOfHours = resultSet.getDouble("number_of_hours");
                    double totalAmount = resultSet.getDouble("total_amount");
                    boolean isPaid = resultSet.getBoolean("is_paid");
        
                    System.out.println("Invoice Number: " + invoiceNumber);
                    System.out.println("Client Name: " + clientName);
                    System.out.println("Invoice Date: " + invoiceDate);
                    System.out.println("Due Date: " + dueDate);
                    System.out.println("Service Name: " + serviceName);
                    System.out.println("Number of Hours: " + numberOfHours);
                    System.out.println("Total Amount: " + totalAmount);
                    System.out.println("Paid: " + (isPaid ? "Yes" : "No"));
                    System.out.println();
                }
            } catch (SQLException e) {
                System.out.println("\nError viewing invoices: " + e.getMessage());
            }
        }
        
        private static void generateInvoice() {
            System.out.println("\nGenerate Invoice:");
            System.out.print("Enter client email address: ");
            String clientEmail = scanner.nextLine();
        
            // Check if client exists
            if (!clientExists(clientEmail)) {
                System.out.println("\nClient not found with the given email address.");
                return;
            }
        
            System.out.print("Enter service name: ");
            String serviceName = scanner.nextLine();
        
            // Check if service exists
            if (!serviceExists(serviceName)) {
                System.out.println("\nService not found with the given name.");
                return;
                }
                System.out.print("Enter number of hours: ");
                double hours = scanner.nextDouble();
                scanner.nextLine();
            
                // Calculate total amount
                double hourlyRate = getServiceHourlyRate(serviceName);
                double totalAmount = hours * hourlyRate;
            
                LocalDate invoiceDate = LocalDate.now();
                LocalDate dueDate = invoiceDate.plusDays(30); // Due date 30 days from the invoice date
            
                String clientName = getClientNameByEmail(clientEmail);
            
                String sql = "INSERT INTO invoices (client_name, invoice_date, due_date, service_name, number_of_hours, total_amount, is_paid) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)";
            
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, clientName);
                    statement.setDate(2, Date.valueOf(invoiceDate));
                    statement.setDate(3, Date.valueOf(dueDate));
                    statement.setString(4, serviceName);
                    statement.setDouble(5, hours);
                    statement.setDouble(6, totalAmount);
                    statement.setBoolean(7, false); // Mark as unpaid initially
            
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("\nInvoice generated successfully!");
                    } else {
                        System.out.println("\nFailed to generate invoice.");
                    }
                } catch (SQLException e) {
                    System.out.println("\nError generating invoice: " + e.getMessage());
                }
            }
            
            private static void markInvoiceAsPaid() {
                System.out.print("\nEnter invoice number to mark as paid: ");
                int invoiceNumber = scanner.nextInt();
                scanner.nextLine();
            
                String sql = "UPDATE invoices SET is_paid = ? WHERE invoice_number = ?";
            
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setBoolean(1, true);
                    statement.setInt(2, invoiceNumber);
            
                    int rowsUpdated = statement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("\nInvoice marked as paid.");
                    } else {
                        System.out.println("\nInvoice not found with the given invoice number.");
                    }
                } catch (SQLException e) {
                    System.out.println("\nError marking invoice as paid: " + e.getMessage());
                }
            }
            
            private static void deleteInvoice() {
                System.out.print("\nEnter invoice number to delete: ");
                int invoiceNumber = scanner.nextInt();
                scanner.nextLine();
            
                String sql = "DELETE FROM invoices WHERE invoice_number = ?";
            
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, invoiceNumber);
            
                    int rowsDeleted = statement.executeUpdate();
                    if (rowsDeleted > 0) {
                        System.out.println("\nInvoice deleted successfully!");
                    } else {
                        System.out.println("\nInvoice not found with the given invoice number.");
                    }
                } catch (SQLException e) {
                    System.out.println("\nError deleting invoice: " + e.getMessage());
                }
            }
            
            private static void showAnalytics() {
                System.out.println("Analytics Menu:");
                System.out.println("1. Time Period for Analytics");
                System.out.println("2. Total Income");
                System.out.println("3. Most Popular Service");
                System.out.println("4. Top Client");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
            
                switch (choice) {
                    case 1:
                        timePeriodAnalytics();
                        break;
                    case 2:
                        totalIncomeAnalytics();
                        break;
                    case 3:
                        mostPopularServiceAnalytics();
                        break;
                    case 4:
                        topClientAnalytics();
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                }
            }
            
            private static void timePeriodAnalytics() {
                System.out.println("\nEnter start date for analytics (YYYY-MM-DD): ");
                String startDateStr = scanner.nextLine();
                LocalDate startDate = LocalDate.parse(startDateStr);
            
                System.out.println("Enter end date for analytics (YYYY-MM-DD): ");
                String endDateStr = scanner.nextLine();
                LocalDate endDate = LocalDate.parse(endDateStr);
            
                String sql = "SELECT SUM(total_amount) AS total_income FROM invoices WHERE invoice_date >= ? AND invoice_date <= ?";
            
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setDate(1, Date.valueOf(startDate));
                    statement.setDate(2, Date.valueOf(endDate));
            
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        double totalIncome = resultSet.getDouble("total_income");
                        System.out.println("\nTotal Income for the specified period: $" + totalIncome);
                    } else {
                        System.out.println("\nNo data available for the specified period.");
                    }
                } catch (SQLException e) {
                    System.out.println("\nError calculating total income: " + e.getMessage());
                }
            }
            
            private static void totalIncomeAnalytics() {
                String sql = "SELECT SUM(total_amount) AS total_income FROM invoices";
            
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(sql)) {
                        if (resultSet.next()) {
                            double totalIncome = resultSet.getDouble("total_income");
                            System.out.println("\nTotal Income: $" + totalIncome);
                        } else {
                            System.out.println("\nNo data available.");
                        }
                    } catch (SQLException e) {
                        System.out.println("\nError calculating total income: " + e.getMessage());
                    }
                }
                
                private static void mostPopularServiceAnalytics() {
                    String sql = "SELECT service_name, SUM(number_of_hours) AS total_hours " +
                                 "FROM invoices " +
                                 "GROUP BY service_name " +
                                 "ORDER BY total_hours DESC " +
                                 "LIMIT 1";
                
                    try (Statement statement = connection.createStatement();
                         ResultSet resultSet = statement.executeQuery(sql)) {
                
                        if (resultSet.next()) {
                            String mostPopularService = resultSet.getString("service_name");
                            double totalHours = resultSet.getDouble("total_hours");
                            System.out.println("\nMost Popular Service: " + mostPopularService);
                            System.out.println("Total Hours: " + totalHours);
                        } else {
                            System.out.println("\nNo data available.");
                        }
                    } catch (SQLException e) {
                        System.out.println("\nError finding most popular service: " + e.getMessage());
                    }
                }
                
                private static void topClientAnalytics() {
                    String sql = "SELECT client_name, SUM(total_amount) AS total_spent " +
                                 "FROM invoices " +
                                 "GROUP BY client_name " +
                                 "ORDER BY total_spent DESC " +
                                 "LIMIT 1";
                
                    try (Statement statement = connection.createStatement();
                         ResultSet resultSet = statement.executeQuery(sql)) {
                
                        if (resultSet.next()) {
                            String topClient = resultSet.getString("client_name");
                            double totalSpent = resultSet.getDouble("total_spent");
                            System.out.println("\nTop Client: " + topClient);
                            System.out.println("Total Amount Spent: $" + totalSpent);
                        } else {
                            System.out.println("\nNo data available.");
                        }
                    } catch (SQLException e) {
                        System.out.println("\nError finding top client: " + e.getMessage());
                    }
                }
                
                private static boolean clientExists(String email) {
                    String sql = "SELECT * FROM clients WHERE email_address = ?";
                
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, email);
                        ResultSet resultSet = statement.executeQuery();
                        return resultSet.next();
                    } catch (SQLException e) {
                        System.out.println("\nError checking client existence: " + e.getMessage());
                        return false;
                    }
                }
                
                private static boolean serviceExists(String serviceName) {
                    String sql = "SELECT * FROM services WHERE service_name = ?";
                
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, serviceName);
                        ResultSet resultSet = statement.executeQuery();
                        return resultSet.next();
                    } catch (SQLException e) {
                        System.out.println("\nError checking service existence: " + e.getMessage());
                        return false;
                    }
                }
                
                private static double getServiceHourlyRate(String serviceName) {
                    String sql = "SELECT hourly_rate FROM services WHERE service_name = ?";
                
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, serviceName);
                        ResultSet resultSet = statement.executeQuery();
                
                        if (resultSet.next()) {
                            return resultSet.getDouble("hourly_rate");
                        }
                    } catch (SQLException e) {
                        System.out.println("\nError getting service hourly rate: " + e.getMessage());
                    }
                
                    return 0.0;
                }
                
                private static String getClientNameByEmail(String email) {
                    String sql = "SELECT full_name FROM clients WHERE email_address = ?";
                
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, email);
                        ResultSet resultSet = statement.executeQuery();
                
                        if (resultSet.next()) {
                            return resultSet.getString("full_name");
                        }
                    } catch (SQLException e) {
                        System.out.println("\nError getting client name: " + e.getMessage());
                    }
                
                    return "";
                }
            }                
            
        
