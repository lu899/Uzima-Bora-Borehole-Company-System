package database;
import java.sql.*;
import javax.swing.*;

public class DatabaseManager {
    private static Connection con = null;

    public static Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/uzima_borehole_db";
        String user = "root";
        String password = "Password_22";
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Succesfully connected to Database!!");
        } catch (ClassNotFoundException e){
            System.out.println("Couldn't find class: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to connect to database: " + e.getMessage());
        }
        return con;
    }
}
