import java.io.*;
import java.util.*;
import java.sql.*;
public class Admin {

    public void adminPage()throws IOException
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int ch;
        do{
             
        System.out.println("*********************************************************************************");
        System.out.println("WELCOME TO THE ADMINISTRATOR MENU");
        System.out.println("*********************************************************************************");
        System.out.println("Choose the task you want to carry out");
        System.out.println("1.Manage Products");
        System.out.println("2.Remove Customers");
        System.out.println("3.Logout From The System");
        System.out.println("ENTER YOUR CHOICE : ");
        ch=Integer.parseInt(br.readLine());
        if(ch==1){
            Products obj = new Products();
            obj.productsPage();
        }
        else if(ch==2){
            removeCustomers();
    }
    else if(ch==3){
        System.out.println("THANK YOU!!!!!!!!! FOR USING THE SERVICE!!!!!!");
        new Shop();
    }
    else{
        System.out.println("Wrong Choice Please Try Again");
    }
    }while(ch!=3);
}
private void removeCustomers() {
       // Using try-with-resources to ensure proper handling of resources (connection, statements, and result sets)
    try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/OnlineShop", "root",null);
         PreparedStatement pstSelect = con.prepareStatement("SELECT * FROM CustomerInfo WHERE CUSTOMER_ID = ?");
         PreparedStatement pstDelete = con.prepareStatement("DELETE FROM CustomerInfo WHERE CUSTOMER_ID = ?")
         ) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Customer ID to search:");
        String customerId = br.readLine();

        // Search for the customer
        pstSelect.setString(1, customerId);
        try (ResultSet rs = pstSelect.executeQuery()) {
            if (rs.next()) {
                // Customer found, display their record
                System.out.println("Customer Found:");
                System.out.println("Customer ID: " + rs.getString("CUSTOMER_ID"));
                System.out.println("Customer Name: " + rs.getString("CUST_NAME"));
                System.out.println("Customer Address: " + rs.getString("CUST_ADDRESS"));
                System.out.println("Customer Email: "+rs.getString("CUST_EMAIL"));
                System.out.println("Customer PhoneNumber: "+rs.getString("CUST_PHONE"));

                // Confirm deletion of the customer record
                System.out.println("Do you want to delete this customer? (yes/no)");
                String confirmation = br.readLine();
                if (confirmation.equalsIgnoreCase("yes")) {
                    // Delete the customer record
                    pstDelete.setString(1, customerId);
                    int rowsAffected = pstDelete.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Customer record deleted successfully.");
                    } else {
                        System.out.println("Failed to delete customer record.");
                    }
                } else {
                    System.out.println("Deletion canceled.");
                }
            } else {
                System.out.println("Customer not found with ID: " + customerId);
            }
        }
        //This line catches and handles any SQLExcepton or IOException that might occur during the execution of the database operations or while reading user input .
        //If an exception occrus it prints the stack trace for debugging purposes.
    } catch (SQLException | IOException e) {
        e.printStackTrace();
    }
}


}