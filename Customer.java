
import java.io.*;
import java.util.*;
import java.sql.*;

public class Customer {
    public Customer(String id) throws IOException {
        String userid = id;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int ch;
        Cart c = new Cart();
        do {
            System.out.println("*********************************************************************************");
            System.out.println("WELCOME TO THE CUSTOMER MENU");
            System.out.println("*********************************************************************************");
            System.out.println("1.VIEW ALL PRODUCTS AVAILABLE");
            System.out.println("2.ADD PRODUCT TO CART");
            System.out.println("3.REMOVE PRODUCT FROM CART");
            System.out.println("4.PROCEED TO PAYEMENT");
            System.out.println("5.LOGOUT");
            System.out.println("Please Enter Your Choice : ");
            ch = Integer.parseInt(br.readLine());
            if (ch == 1) {
                viewAllProducts();
            } else if (ch == 2) {
                c.addToCart();
                c.viewCart();
            } else if (ch == 3) {
                c.removeFromCart();
            } else if (ch == 4) {
                c.checkout(userid);
            } else if (ch == 5) {
                return;
            } else {
                System.out.println("WRONG CHOICE PLEASE TRY AGAIN");
            }
        } while (ch != 5);

    }

    public void viewAllProducts() {
        try
             {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/OnlineShop", "root", null);
                PreparedStatement pst = con.prepareStatement("SELECT * FROM ProductInfo");
                ResultSet rs = pst.executeQuery();
            // BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("********************************************************");
            System.out.println("PRODUCTS IN THE INVENTORY");
            System.out.println("********************************************************");
            System.out.printf("%-15s %-30s %-10s%n", "PRODUCT ID", "PRODUCT NAME", "PRODUCT PRICE");
            System.out.println("--------------------------------------------------------");

            // This while loop iterates through each row of the result set obtained from the
            // ProductInfo table . For each row, it retrieves the product details and prints
            // them in the appropriate format using printf
            // This Process continues unitll all rows in the result set have been processed.
            while (rs.next()) {
                String productId = rs.getString("PRODUCT_ID");
                String productName = rs.getString("PRODUCT_NAME");
                double productPrice = rs.getDouble("PRODUCT_PRICE");

                System.out.printf("%-15s %-30s %-10.2f%n", productId, productName, productPrice);
            }
        } catch (SQLException  | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
