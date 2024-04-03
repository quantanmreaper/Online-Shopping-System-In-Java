import java.io.*;
import java.util.*;
import java.sql.*;

public class Shop {

    public static void main(String[] args) throws IOException
    {
          BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
          System.out.println("WELCOME TO THE SHOPPING SYSTEM");
          int ch;
          do{
            System.out.println("-------------------------------------------------------------------------------");
                             System.out.println("WELCOME TO THE SHOPPING APPLICATION");
            System.out.println("-------------------------------------------------------------------------------");
           // System.out.println("1.REGISTER AS AN ADMIN");
            //System.out.println("2.REGISTER AS A CUSTOMER");
            System.out.println("1.SIGN UP TO THE APPLICATION");
            System.out.println("2.LOGIN TO THE APPLICATION");
            System.out.println("3.EXIT");
            System.out.println("Please Enter Your Choice: ");
         
          ch = Integer.parseInt(br.readLine());
          if(ch==1){
           // registerAdmin();
           signUp();
          }
         /*  else if(ch==2){
            //registerCustomer();
          } */

          else if(ch==2){
            userLogin();
          }
          else if(ch==3){
            System.out.println("THANK YOU FOR USING THIS APPLICAITON!!!!!!!");
          }
          else{
            System.out.println("WRONG CHOICE , PLEASE ENTER AGAIN");
          }
        }
          while(ch!=3);
    }

          
    /* static void registerAdmin() throws IOException {
        Connection con = null;
        PreparedStatement pst = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            //Establishing  a connection with the database.
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/OnlineShop", "root", null);

            //Prompting the user to enter  admin details and storing them in variables.
            System.out.println("Enter Admin ID:");
            int adminId = Integer.parseInt(br.readLine());
            System.out.println("Enter Admin Name:");
            String adminName = br.readLine();

            //Executing sql  query for adding an admin into the table.
            String sql = "INSERT INTO AdminInfo (ADMIN_ID, ADMIN_NAME) VALUES (?, ?)";
            pst = con.prepareStatement(sql);
            pst.setInt(1, adminId);
            pst.setString(2, adminName);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Admin registered successfully!");
            } else {
                System.out.println("Failed to register admin!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } */

        /* static void registerCustomer()throws IOException
        {
            Connection con = null;
            PreparedStatement pst = null;
            //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Scanner input = new Scanner(System.in);
            try{
                //Establishing Database Connection
                
                 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/OnlineShop", "root", null);
                
                 //Taking input for customer tables entities
                // System.out.println("CUSTOMER ID ");
                 
                 System.out.println("CUSTOMER NAME");
            
                String custName=input.nextLine();
                 System.out.println("CUSTOMER ADDRESS");
                 
                 String custAddress=input.nextLine();
                 System.out.println("CUSTOMER EMAIL");
                 
                 String custEmail=input.nextLine();
                 System.out.println("CUSTOMER PHONE NUMBER");
                
                 String custPhone=input.nextLine();
                
                 // SQL query to insert customer information into CustomerInfo table
                String sql = "INSERT INTO CustomerInfo ( CUST_NAME, CUST_ADDRESS, CUST_EMAIL, CUST_PHONE) VALUES (?, ?, ?, ?)";
                pst = con.prepareStatement(sql);
                pst.setString(1, custName);
                pst.setString(2, custAddress);
                pst.setString(3, custEmail);
                pst.setString(4,custPhone);
                // Executing the query
                int rowsAffected = pst.executeUpdate();
                
                //Appropritate messages being printed to the console to inform the user whether the registration was successfull or not.
                if (rowsAffected > 0) {
                    System.out.println("Customer registered successfully!");
                } else {
                    System.out.println("Failed to register Customer!");
                }
                    
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Closing resources for security and memory managment purposes hence promoting efficient utilization of memory
                try {
                    if (pst != null) pst.close();
                    if (con != null) con.close();
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                // Closing BufferedReader
        /* if (input != null) {
               input.close();
            } */
        //}
    //} */
    static void signUp()throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try 
        {
            //Registering the driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Connecting to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/OnlineShop", "root",null);
            //SQL Query that needs to be executed
            PreparedStatement pstSelect = con.prepareStatement("SELECT USER_ID FROM LoginInfo WHERE USER_ID = ?");
            PreparedStatement pst = con.prepareStatement("INSERT INTO LoginInfo (USER_ID, USER_PASSWORD ,USER_TYPE) VALUES(?, ?, ?)");
            
            System.out.println("ENTER USER ID : ");
            int userid=Integer.parseInt(br.readLine());
            //Verifying whether the userID already exists or not
             // Check if userID already exists
        pstSelect.setInt(1, userid);
        ResultSet rs = pstSelect.executeQuery();
        if (rs.next()) {
            System.out.println("User ID already exists. Please Enter A Different One.");
            return; // Exit method if userID already exists
        }

            System.out.println("ENTER USER PASSWORD : ");
            String password=br.readLine();

            System.out.println("ENTER USER TYPE A(ADMIN) OR C(CUSTOMER)");
            String userType = br.readLine();
            //char userType = br.readLine();
            if (userType.equalsIgnoreCase("A")) {
                System.out.println("You've chosen ADMIN user type.");
                // Add your admin-related logic here
            } else if (userType.equalsIgnoreCase("C")) {
                System.out.println("You've chosen CUSTOMER user type.");
                // Add your customer-related logic here
            } else {
                System.out.println("Invalid user type entered.");
            }
            pst.setInt(1, userid);
            pst.setString(2, password);
            pst.setString(3, userType);

            int rowsAffected = pst.executeUpdate();
            if(rowsAffected>0){
                System.out.println("USER  CREATED SUCCESSFULLY!");
            }
            else{
                System.out.println("FAILED TO CREATE USER PLEASE TRY AGAIN");
            }
        }catch(ClassNotFoundException | SQLException ex)
        {
            ex.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
        static void userLogin() throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Connection con = null;
            PreparedStatement pst = null;
            ResultSet rs = null;
    
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/OnlineShop", "root",null);
    
                System.out.println("Enter User ID:");
                String userId = br.readLine();
                System.out.println("Enter Password:");
                String password = br.readLine();
    
                String query = "SELECT * FROM LoginInfo WHERE USER_ID = ? AND USER_PASSWORD = ?;";
                pst = con.prepareStatement(query);
                pst.setString(1, userId);
                pst.setString(2, password);
                rs = pst.executeQuery();
    
                if (rs.next()) {
                    if (rs.getString(3).equals("A")) {
                       
                        // Redirect to admin page
                        Admin obj = new Admin();
                        obj.adminPage();

                    } else if (rs.getString(3).equals("C")) {
                        
                       // Redirect to customer page
                        new Customer(userId);
                    }
                } else {
                    System.out.println("Invalid credentials. Please try again.");
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (pst != null) pst.close();
                    if (con != null) con.close();
                } catch (SQLException  e) {
                    e.printStackTrace();
                }
            }
        }

    
}
 