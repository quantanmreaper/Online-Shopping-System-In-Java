import java.io.*;
import java.util.*;
import java.sql.*;

public class Cart {
   
    // Declaring and making use of ArrayList to store the items in cart .
    private ArrayList<Product> cartItems = new ArrayList<Product>();
    // public Cart() {

    // cartItems = new ArrayList<>();
    // }

    private class Product {
        public String id, name;
        public double price;
        public int quantity;

        public Product(String id, String name, double price, int quantity) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
    }

    static private int billID = 0;

    public void addToCart() {
        try (
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/OnlineShop", "root", null);
                PreparedStatement pst = con.prepareStatement("SELECT * FROM ProductInfo WHERE PRODUCT_ID = ?");) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String ch;
            do {
                System.out.print("Enter a Product Id for the product you want to add in the Cart: ");
                String productId = br.readLine();

                System.out.print("Enter the quantity for Product " + productId + ": ");
                int quantity = Integer.parseInt(br.readLine());

                pst.setString(1, productId);
                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    // using item as an array that stores a values of data type String and passing
                    // the array to list
                    Product item = new Product(productId, rs.getString("PRODUCT_NAME"), rs.getDouble("PRODUCT_PRICE"),
                            quantity);
                    cartItems.add(item);
                    System.out.println("Product added to cart successfully.");
                } else {
                    System.out.println("Product with ID " + productId + " not found.");
                }

                System.out.print("Do You Want To Add More Products To Cart? (Yes/No): ");
                ch = br.readLine();
            } while (ch.equalsIgnoreCase("Yes"));

            // new Bills(cartItems); // Pass the cartItems to Bills constructor to generate
            // the bill
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // Method to view the cart items
    public void viewCart() {
        if (cartItems.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Cart Items:");
            System.out.println("--------------------------------------------------------");
            System.out.printf("%-15s %-30s %-10s %-10s%n", "PRODUCT ID", "PRODUCT NAME", "PRODUCT PRICE", "QUANTITY");
            System.out.println("--------------------------------------------------------");
            for (Product item : cartItems) {
                System.out.printf("%-15s %-30s %-10f %-10d%n", item.id, item.name, item.price, item.quantity);
            }
        }
    }

    public void removeFromCart() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // System.out.println("ITEMS IN THE CART BEFORE REMOVAL " + cartItems);
        viewCart();

        if (cartItems.isEmpty())
            return;

        System.out.print("Enter the Product ID to remove from the cart: ");
        String productId = br.readLine().trim(); // Trim the input to remove leading and trailing whitespaces

        boolean found = false;
        Iterator<Product> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            Product item = iterator.next();
            if (item.id.equals(productId)) {
                iterator.remove();
                System.out.println("Product with ID " + productId + " removed from the cart.");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Product with ID " + productId + " not found in the cart.");
        } else {
            // Show the updated cart only if it's not empty
            if (!cartItems.isEmpty()) {
                viewCart();
            }
        }

    }

    public void checkout(String userid)throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("User ID: " + userid);
        // a random number
        viewCart();


        double totalAmount = 0;
        for (Product p : cartItems) {
            totalAmount += p.price * p.quantity;
        }

        // Cart payed for
        System.out.printf("Bill Number: %-4d/n", billID);
        System.out.println("===================================================");
        System.out.println("Total Amount: " + totalAmount);
        System.out.println("===================================================");
        
        System.out.println("Do You Continue To Shop (Yes/No)");

        try {
            String input = br.readLine();
            if(input.equalsIgnoreCase("Yes")){
                return;
            }
            else if(input.equalsIgnoreCase("No")){
                System.out.println("YOU HAVE SUCCESSFULLY PAID YOUR BILL! Enjoy your shopping!");
                
               /*  Double finalamount=Double.parseDouble(br.readLine());
                do{
                    if(finalamount!=totalAmount){
                        System.out.println("Wrong Amount  ");
                    }
                }while(finalamount!=totalAmount); */
            }
    
        } catch (IOException e) {
           e.printStackTrace();
            //  handle exception
        }
        
        cartItems.clear();
        billID++;
    }

}
