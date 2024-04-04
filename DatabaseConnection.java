import java.sql.*;

public class DatabaseConnection {

    public  void makeDatabase()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver"); // Load the driver
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/OnlineShop", "root",null);
    
            //Creating the database
            PreparedStatement pstCreate = con.prepareStatement("CREATE DATABASE OnlineShop");
            pstCreate.executeUpdate();
            System.out.println("The database has been created successfully!"); 

            //Selecting database OnlineShop to use it
            PreparedStatement pstUse = con.prepareStatement("use OnlineShop");
            pstUse.executeUpdate();
        
            //Creating the table LoginInfo 
            PreparedStatement pstLogin = con.prepareStatement("CREATE TABLE LoginInfo (USER_ID int primary key not null , USER_PASSWORD varchar(50) not null, USER_TYPE varchar(1) not null)");
            pstLogin.executeUpdate();

            //Creating the table ProductInfo 
            PreparedStatement pstProduct = con.prepareStatement("CREATE TABLE ProductInfo (PRODUCT_ID int primary key not null, PRODUCT_NAME varchar(50), PRODUCT_PRICE decimal(10,2))");
            pstProduct.executeUpdate();

        }catch(SQLException | ClassNotFoundException e){
            System.out.println("Error while connecting to the database");
            e.printStackTrace();
        }
    }

}
