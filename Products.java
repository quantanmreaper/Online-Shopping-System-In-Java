import java.io.*;
import java.util.*;
import java.sql.*;

public class Products {
    
    public  void productsPage()throws IOException
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int ch;
        do
        {
            System.out.println("********************************************************");
            System.out.println("WELCOME TO THE PRODUCTS PAGE");
            System.out.println("********************************************************");
            System.out.println("1.ADD PRODUCTS");
            System.out.println("2.REMOVE PRODUCTS");
            System.out.println("3.VIEW ALL PRODUCTS IN THE INVENTORY");
            System.out.println("4.SEARCH FOR A PARTICULAR PRODUCT");
            System.out.println("5.LOG OUT");
            System.out.println("ENTER YOUR CHOICE: ");
            ch=Integer.parseInt(br.readLine());
            if(ch==1){
                addProducts();
            }
            else if(ch==2){
                removeProducts();
            }
            else if(ch==3){
                viewAllProducts();
            }
            else if(ch==4){
                searchProducts();
            }
            else if(ch==5){
                System.out.println("YOU HAVE SUCCESSFULLY LOGGED OUT");
                //new Shop();
            }
            else{
                System.out.println("WRONG CHOICE PLEASE TRY AGAIN");
            }
        }
        while(ch!=5);
          
        }
        public void addProducts() throws IOException {
                   // Using try-with-resources to ensure proper handling of
                   // resources (connection, statements, and result sets)
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/OnlineShop", "root", null);
                 PreparedStatement pstSelect = con.prepareStatement("SELECT PRODUCT_ID FROM ProductInfo WHERE PRODUCT_ID = ?");
                 PreparedStatement pst = con.prepareStatement("INSERT INTO ProductInfo (PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE) VALUES (?, ?, ?)")
                 ) {
                
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                
                //Prompting The User TO Enter Product Details
                System.out.println("Enter Product ID:");
                int productId = Integer.parseInt(br.readLine());

                pstSelect.setInt(1, productId);
                //verifying whether the product with the product id already exists or not
                ResultSet rs = pstSelect.executeQuery();
                if(rs.next()){
                    System.out.println("The Product With These Product Id already exists please enter an appropriate ProductId");
                    return;
                }


                
                System.out.println("Enter Product Name:");
                String productName = br.readLine();
                
                //Prompting the user to enter the correct product price until they enter the correct price which should be greater than zero
                System.out.println("Enter Product Price:");
                double productPrice = Double.parseDouble(br.readLine());
                while(productPrice<=0){
                    System.out.println("Invalid Entry! Please enter a positive number.");
                    productPrice = Double.parseDouble(br.readLine());
                }
                // Set parameters for the prepared statement
                pst.setInt(1, productId);
                pst.setString(2, productName);
                pst.setDouble(3, productPrice);
                
                // Execute the SQL query to insert the product details
                int rowsAffected = pst.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Product added successfully.");
                } else {
                    System.out.println("Failed to add product.");
                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
        public void removeProducts() 
        {
            //using getConnecion method to connect with the database . This is used in every java file used in the program
            //PreparedStatement used for managing SQL queries
            //executeQuery method to execute the SQL querires to get the desired results
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/OnlineShop", "root", null);
                 PreparedStatement pstSelect = con.prepareStatement("SELECT* FROM ProductInfo WHERE PRODUCT_ID = ?");
                 PreparedStatement pstDelete = con.prepareStatement("DELETE FROM ProductInfo WHERE PRODUCT_ID = ?")
                 ) {
                
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Enter Product ID That You Would Like To Remove:");
                String productId = br.readLine();
    
                pstSelect.setString(1, productId);
              
                try (ResultSet rs = pstSelect.executeQuery()) {
                    if (rs.next()) {
                        // IF Product is found, display its record
                        System.out.println("Product Found:");
                        System.out.println("Product ID: " + rs.getString("PRODUCT_ID"));
                        System.out.println("Product Name: " + rs.getString("PRODUCT_NAME"));
                        System.out.println("Product Price:" + rs.getString("PRODUCT_PRICE"));
                       
        
                        // Confirm deletion from the user
                        System.out.println("Do you want to delete this Product? (yes/no)");
                        String confirmation = br.readLine();
                        if (confirmation.equalsIgnoreCase("yes")) {
                            // Delete the product 
                            pstDelete.setString(1, productId);
                            int rowsAffected = pstDelete.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Product  deleted successfully.");
                            } else {
                                System.out.println("Failed to delete customer record.");
                            }
                        } else {
                            System.out.println("Deletion canceled.");
                        }
                    } else {
                        System.out.println("Product with Product Id : " + productId + "not found");
                    }
                }
                //This line catches and handles any SQLExcepton or IOException that might occur during the execution of the database operations or while reading user input .
                //If an exception occrus it prints the stack trace for debugging purposes.
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }    
        public void viewAllProducts(){
            try(
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/OnlineShop", "root", null);
                PreparedStatement pst = con.prepareStatement("SELECT * FROM ProductInfo");
                ResultSet rs = pst.executeQuery()          
                ){
               // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
               System.out.println("********************************************************");
               System.out.println("PRODUCTS IN THE INVENTORY");
               System.out.println("********************************************************");
               System.out.printf("%-15s %-30s %-10s%n", "PRODUCT ID", "PRODUCT NAME", "PRODUCT PRICE");
               System.out.println("--------------------------------------------------------");
               
               //This while loop iterates through each row of the result set obtained from the
               //ProductInfo table . For each row, it retrieves the product details and prints them in the appropriate format using printf
               //This Process continues unitll all rows in the result set have been processed.
               while (rs.next()) {
                   String productId = rs.getString("PRODUCT_ID");
                   String productName = rs.getString("PRODUCT_NAME");
                   double productPrice = rs.getDouble("PRODUCT_PRICE");
                   
                   System.out.printf("%-15s %-30s %-10.2f%n", productId, productName, productPrice);
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }
            }

        public void searchProducts(){
            try(
                  Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/OnlineShop", "root", null);
                  PreparedStatement pst = con.prepareStatement("SELECT * FROM ProductInfo WHERE PRODUCT_ID = ?")
            ){
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter a Product Id for the product you want to search");
                String productId=br.readLine();
                pst.setString(1, productId);

                try( ResultSet rs = pst.executeQuery()){
                    if (rs.next()) {
                        // Product found, display its record
                        System.out.println("Product Found:");
                        System.out.println("Product ID: " + rs.getString("PRODUCT_ID"));
                        System.out.println("Product Name: " + rs.getString("PRODUCT_NAME"));
                        System.out.println("Product Price:" + rs.getString("PRODUCT_PRICE"));
                    }  else {
                        System.out.println("no product found");
                    }
                }

                }catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
 }    

