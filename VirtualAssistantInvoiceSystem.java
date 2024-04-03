import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class VirtualAssistantInvoiceSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Client> clients = new ArrayList<>();
    private static ArrayList<Service> services = new ArrayList<>();
    private static ArrayList<Invoice> invoices = new ArrayList<>();
    private static int invoiceCounter = 1000;
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

        Client client = new Client(fullName, emailAddress, phoneNumber, billingAddress, paymentTerms, serviceType, serviceFrequency);
        clients.add(client);
        System.out.println("\nClient added successfully!");
    }

    private static void viewClients() {
        System.out.println("\nList of Clients:");
        for (Client client : clients) {
            System.out.println(client);
        }
    }

    private static void updateClient() {
        System.out.print("\nEnter client email address to update:");
        String email = scanner.nextLine();
        boolean found = false;
        for (Client client : clients) {
            if (client.getEmailAddress().equalsIgnoreCase(email)) {
                System.out.print("Enter new Full Name: ");
                client.setFullName(scanner.nextLine());
                System.out.print("Enter new Phone Number: ");
                client.setPhoneNumber(scanner.nextLine());
                System.out.print("Enter new Billing Address: ");
                client.setBillingAddress(scanner.nextLine());
                System.out.print("Enter new Payment Terms: ");
                client.setPaymentTerms(scanner.nextLine());
                System.out.print("Enter new Type of Service Required: ");
                client.setServiceType(scanner.nextLine());
                System.out.print("Enter new Frequency of Service: ");
                client.setServiceFrequency(scanner.nextLine());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("\nClient not found with the given email address.");
        } else {
            System.out.println("\nClient information updated successfully.");
        }
    }

    private static void deleteClient() {
        System.out.print("\nEnter client email address to delete:");
        String email = scanner.nextLine();
        clients.removeIf(client -> client.getEmailAddress().equalsIgnoreCase(email));
        System.out.println("\nClient deleted successfully.");
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

        Service service = new Service(serviceName, hourlyRate);
        services.add(service);
        System.out.println("\nService added successfully.");
    }

    private static void viewServices() {
        System.out.println("\nList of Services:");
        for (Service service : services) {
            System.out.println(service);
        }
    }

    private static void updateService() {
        System.out.println("\nEnter service name to update:");
        String serviceName = scanner.nextLine();
        boolean found = false;
        for (Service service : services) {
            if (service.getServiceName().equalsIgnoreCase(serviceName)) {
                System.out.print("Enter new Service Name: ");
                service.setServiceName(scanner.nextLine());
                System.out.print("Enter new Hourly Rate: ");
                service.setHourlyRate(scanner.nextDouble());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("\nService not found with the given name.");
        } else {
            System.out.println("\nService information updated successfully.");
        }
    }

    private static void deleteService() {
        System.out.println("\nEnter service name to delete:");
        String serviceName = scanner.nextLine();
        services.removeIf(service -> service.getServiceName().equalsIgnoreCase(serviceName));
        System.out.println("\nService deleted successfully.");
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
        System.out.println("\nList of Invoices:");
        for (Invoice invoice : invoices) {
            System.out.println(invoice);
        }
    }

    private static void generateInvoice() {
        System.out.println("\nGenerate Invoice:");
        System.out.print("Enter client email address: ");
        String clientEmail = scanner.nextLine();
        Client client = findClientByEmail(clientEmail);
        if (client == null) {
            System.out.println("\nClient not found with the given email address.");
            return;
        }
        System.out.print("Enter service name: ");
        String serviceName = scanner.nextLine();
        Service service = findServiceByName(serviceName);
        if (service == null) {
            System.out.println("\nService not found with the given name.");
            return;
        }

        System.out.print("Enter number of hours: ");
        double hours = scanner.nextDouble();
        scanner.nextLine();
    
        double totalAmount = hours * service.getHourlyRate();
        LocalDate invoiceDate = LocalDate.now();
        LocalDate dueDate = invoiceDate.plusDays(30); // Due date 30 days from the invoice date
    
        Invoice invoice = new Invoice(client.getFullName(), generateInvoiceNumber(), invoiceDate, dueDate, serviceName, hours, totalAmount);
        invoices.add(invoice);
        service.addToTotalHoursBilled(hours);
    
        System.out.println("\nInvoice generated successfully:");
        System.out.println(invoice);
    }

    private static void markInvoiceAsPaid() {
        System.out.print("\nEnter invoice number to mark as paid:");
        int invoiceNumber = scanner.nextInt();
        scanner.nextLine();
    
        boolean found = false;
        for (Invoice invoice : invoices) {
            if (invoice.getInvoiceNumber() == invoiceNumber) {
                invoice.markAsPaid();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("\nInvoice not found with the given invoice number.");
        } else {
            System.out.println("\nInvoice marked as paid.");
        }
    }

    private static void deleteInvoice() {
        System.out.println("\nEnter invoice number to delete:");
        int invoiceNumber = scanner.nextInt();
        scanner.nextLine();
    
        invoices.removeIf(invoice -> invoice.getInvoiceNumber() == invoiceNumber);
        System.out.println("\nInvoice deleted successfully.");
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
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Enter end date for analytics (YYYY-MM-DD): ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());
    
        double totalIncome = invoices.stream()
                .filter(invoice -> invoice.getInvoiceDate().isAfter(startDate) && invoice.getInvoiceDate().isBefore(endDate))
                .mapToDouble(Invoice::getTotalAmount)
                .sum();
    
        System.out.println("\nTotal Income for the specified period: $" + totalIncome);
    }

    private static void totalIncomeAnalytics() {
        double totalIncome = invoices.stream()
            .mapToDouble(Invoice::getTotalAmount)
            .sum();

    System.out.println("\nTotal Income: $" + totalIncome);
    }

    private static void mostPopularServiceAnalytics() {
        Map<String, Long> serviceCounts = invoices.stream()
            .collect(Collectors.groupingBy(Invoice::getServiceName, Collectors.counting()));

        String mostPopularService = serviceCounts.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("\nNo services");

    System.out.println("\nMost Popular Service: " + mostPopularService);
    }

    private static void topClientAnalytics() {
        Map<String, Double> clientTotalAmounts = invoices.stream()
            .collect(Collectors.groupingBy(Invoice::getClientName, Collectors.summingDouble(Invoice::getTotalAmount)));

        String topClient = clientTotalAmounts.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("\nNo clients");

    System.out.println("\nTop Client: " + topClient);
    }
    
    private static Client findClientByEmail(String email) {
        for (Client client : clients) {
            if (client.getEmailAddress().equalsIgnoreCase(email)) {
                return client;
        }
    }
                return null;
}

    private static Service findServiceByName(String serviceName) {
        for (Service service : services) {
            if (service.getServiceName().equalsIgnoreCase(serviceName)) {
                return service;
        }
    }
                return null;
}

    private static int generateInvoiceNumber() {
        return invoiceCounter++;
}

    }//end sa main class


class Client {
    private String fullName;
    private String emailAddress;
    private String phoneNumber;
    private String billingAddress;
    private String paymentTerms;
    private String serviceType;
    private String serviceFrequency;

    public Client(String fullName, String emailAddress, String phoneNumber, String billingAddress, String paymentTerms, String serviceType, String serviceFrequency) {
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.billingAddress = billingAddress;
        this.paymentTerms = paymentTerms;
        this.serviceType = serviceType;
        this.serviceFrequency = serviceFrequency;
    }

    // Getters and setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceFrequency() {
        return serviceFrequency;
    }

    public void setServiceFrequency(String serviceFrequency) {
        this.serviceFrequency = serviceFrequency;
    }

    @Override
    public String toString() {
        return "Full Name ='" + fullName + '\'' +
                "\nEmail Address ='" + emailAddress + '\'' +
                "\nPhone Number ='" + phoneNumber + '\'' +
                "\nBilling Address ='" + billingAddress + '\'' +
                "\nPayment Terms ='" + paymentTerms + '\'' +
                "\nService Type ='" + serviceType + '\'' +
                "\nService Frequency ='" + serviceFrequency + '\''+"\n";
    }
}

class Service {
    private String serviceName;
    private double hourlyRate;
    private double totalHoursBilled;

    public Service(String serviceName, double hourlyRate) {
        this.serviceName = serviceName;
        this.hourlyRate = hourlyRate;
        this.totalHoursBilled = 0;
    }

    // Getters and setters
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getTotalHoursBilled() {
        return totalHoursBilled;
    }

    public void addToTotalHoursBilled(double hours) {
        this.totalHoursBilled += hours;
    }

    @Override
    public String toString() {
        return "Service Name ='" + serviceName + '\'' +
                "\nHourly Rate =" + hourlyRate +
                "\nTotal Hours Billed =" + totalHoursBilled + "\n";
    }
}

class Invoice {
     private String clientName;
    private int invoiceNumber;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private String serviceName;
    private double numberOfHours;
    private double totalAmount;
    private boolean isPaid;

    public Invoice(String clientName, int invoiceNumber, LocalDate invoiceDate, LocalDate dueDate, String serviceName, double numberOfHours, double totalAmount) {
        this.clientName = clientName;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.dueDate = dueDate;
        this.serviceName = serviceName;
        this.numberOfHours = numberOfHours;
        this.totalAmount = totalAmount;
        this.isPaid = false;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void markAsPaid() {
        this.isPaid = true;
    }

    // Getters
    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getClientName() {
        return clientName;
    }
    

    @Override
    public String toString() {
        return "Client Name ='" + clientName + '\'' +
                "\nInvoice Number =" + invoiceNumber +
                "\nInvoice Date =" + invoiceDate +
                "\nDue Date =" + dueDate +
                "\nService Name ='" + serviceName + '\'' +
                "\nNumber of Hours =" + numberOfHours +
                "\nTotal Amount =" + totalAmount +
                "\nPaid =" + isPaid + "\n";
    }
}