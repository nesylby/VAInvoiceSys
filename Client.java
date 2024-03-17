import java.util.*;
import java.sql.*;

public class Client {
    private int client_id;
    private int contact_information;
    private int total_amount_billed;
    private String client_name;
    private String billing_address;
    private Connection conn;

    // JDBC connection URL, username, and password
    static final String JDBC_URL = "jdbc:mysql://localhost:3306/invoicesys";
    static final String USERNAME = "root";
    static final String PASSWORD = "4G0NF4DEJDJ";

    Client(int client_id, String client_name, int contact_information, int total_amount_billed, String billing_address) {
        this.client_id = client_id;
        this.contact_information = contact_information;
        this.total_amount_billed = total_amount_billed;
        this.client_name = client_name;
        this.billing_address = billing_address;

        try {
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
    }

    public int getCID() {
        return client_id;
    }

    public int getConInf() {
        return contact_information;
    }

    public int getCTotal() {
        return total_amount_billed;
    }

    public String getCName() {
        return client_name;
    }

    public String getCAdd() {
        return billing_address;
    }

    public void setCName(String name) {
        this.client_name = name;
    }

    public void setCTotal(int total) {
        this.total_amount_billed = total;
    }

    public String toString() {
        return client_id + " " + client_name + " " + contact_information + " " + total_amount_billed + " " + billing_address;
    }

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection");
            e.printStackTrace();
        }
    }
}

class Implement {
    public static void main(String[] args) {
        List<Client> c = new ArrayList<Client>();
        Scanner s = new Scanner(System.in);
        int ch;
        try (Connection conn = DriverManager.getConnection(Client.JDBC_URL, Client.USERNAME, Client.PASSWORD);
             Statement stmt = conn.createStatement();) {
            do {
                System.out.println("1. ADD");
                System.out.println("2. VIEW");
                System.out.println("3. UPDATE");
                System.out.println("4. DELETE");
                System.out.println("5. CLIENT BILL");
                System.out.println("0. EXIT");

                System.out.println("Enter Your Choice");
                ch = s.nextInt();
                s.nextLine(); // Consume newline

                switch (ch) {
                    case 1:
                        System.out.print("Enter Client Number: ");
                        int client_id = s.nextInt();
                        s.nextLine(); // Consume newline
                        System.out.print("Enter Client Name: ");
                        String client_name = s.nextLine();
                        System.out.print("Enter Client Contact Information: ");
                        int contact_information = s.nextInt();
                        s.nextLine(); // Consume newline
                        System.out.print("Enter Client Bill: ");
                        int total_amount_billed = s.nextInt();
                        s.nextLine(); // Consume newline
                        System.out.print("Enter Client Billing Address: ");
                        String billing_address = s.nextLine();

                        c.add(new Client(client_id, client_name, contact_information, total_amount_billed, billing_address));
                        break;

                    case 2:
                        System.out.println("--------------------");
                        for (Client e : c) {
                            System.out.println(e);
                        }
                        System.out.println("--------------------");
                        break;

                    case 3:
                        boolean foundUpdate = false;
                        System.out.println("Enter Client Number to Update: ");
                        int updateId = s.nextInt();
                        s.nextLine(); // Consume newline
                        System.out.println("--------------------");
                        ListIterator<Client> li = c.listIterator();
                        while (li.hasNext()) {
                            Client e = li.next();
                            if (e.getCID() == updateId) {
                                System.out.println("Enter New Client Name: ");
                                String updatedName = s.nextLine();
                                System.out.print("Enter New Client Bill: ");
                                int updatedBill = s.nextInt();
                                s.nextLine(); // Consume newline

                                e.setCName(updatedName);
                                e.setCTotal(updatedBill);
                                foundUpdate = true;
                                break;
                            }
                        }
                        if (!foundUpdate) {
                            System.out.println("Record Not Found");
                        } else {
                            System.out.println("Record Updated Successfully!");
                        }
                        System.out.println("--------------------");
                        break;

                    case 4:
                        boolean foundDelete = false;
                        System.out.println("Enter Client Number to Delete: ");
                        int deleteId = s.nextInt();
                        s.nextLine(); // Consume newline
                        System.out.println("--------------------");
                        Iterator<Client> iter = c.iterator();
                        while (iter.hasNext()) {
                            Client e = iter.next();
                            if (e.getCID() == deleteId) {
                                iter.remove();
                                foundDelete = true;
                            }
                        }
                        if (!foundDelete) {
                            System.out.println("Record Not Found");
                        } else {
                            System.out.println("Record Deleted Successfully!");
                        }
                        System.out.println("--------------------");
                        break;

                    case 5:
                        boolean foundBill = false;
                        System.out.println("Enter Client Number for Billing: ");
                        int billId = s.nextInt();
                        s.nextLine(); // Consume newline
                        System.out.println("--------------------");
                        for (Client e : c) {
                            if (e.getCID() == billId) {
                                System.out.println("Client Name: " + e.getCName());
                                System.out.println("Billing Address: " + e.getCAdd());
                                System.out.println("Total Amount Billed: " + e.getCTotal());
                                foundBill = true;
                                // Add your billing logic here
                                break;
                            }
                        }
                        if (!foundBill) {
                            System.out.println("Record Not Found");
                        }
                        System.out.println("--------------------");
                        break;

                    case 0:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid Choice!");
                        break;
                }
            } while (ch != 0);
        } catch (SQLException e) {
            System.out.println("SQL Exception occurred:");
            e.printStackTrace();
        } finally {
            s.close();
        }
    }
}
// this is a comment part 2