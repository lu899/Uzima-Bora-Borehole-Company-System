package database;
import java.sql.*;
import java.util.*;

import javax.swing.*;
import model.*;
import util.PasswordHasher;

public class ClientDAO {
    private List<Client> clients = new ArrayList<>();
    private Connection con = DatabaseManager.getConnection();

    public void insertClient(Client client, char[] password){
        String insertSQL = "INSERT INTO clients(name, telephone, email, address, password, borehole_location, client_category, registration_date) VALUES (?,?,?,?,?,?,?,CURDATE())";

        try (PreparedStatement pst = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            String hashedPassword = PasswordHasher.hashPassword(password);
            pst.setString(1, client.getUsername());
            pst.setString(2, client.getClientTelephone());
            pst.setString(3, client.getUserEmail());
            pst.setString(4, client.getClientAddress());
            pst.setString(5, hashedPassword);
            pst.setString(6, client.getBoreholeLocation());
            pst.setString(7, client.getClientCategory().name());

            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    int clientId = rs.getInt(1);
                    client.setClientId(clientId);
                    client.setPassword(hashedPassword);
                    JOptionPane.showMessageDialog(null, "User Registered Successfully!!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to Register User: " + e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to Register User: " + e.getMessage());
        } finally {
            Arrays.fill(password, '0');
        }
    }

    public Client getClient(String name, char[] password){
        String selectSQL = "SELECT * FROM clients WHERE name LIKE ?";
        try (PreparedStatement pst = con.prepareStatement(selectSQL)) {
            pst.setString(1, "%" + name + "%");

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("password");
                boolean isValid = PasswordHasher.verifyPassword(password, storedHash);
                
                if (isValid) {
                    ClientCategory category = ClientCategory.valueOf(rs.getString("client_category").toUpperCase());
                    Client user = new Client(rs.getString("name"), 
                                         rs.getString("address"), 
                                         rs.getString("telephone"), 
                                         rs.getString("email"), 
                                         rs.getString("borehole_location"),
                                         category
                                        );
                    user.setPassword(storedHash);
                    user.setClientId(rs.getInt("client_id"));
                    JOptionPane.showMessageDialog(null, "User logged in Successfully!!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    return user;
                      
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Password!", "Failure", JOptionPane.WARNING_MESSAGE);
                }       
            } else {
                JOptionPane.showMessageDialog(null, "User not found!", "Failure", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Login failed: " + e.getMessage(), "Failed", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Login failed: " + e.getMessage(), "Failed", JOptionPane.ERROR_MESSAGE);
        } finally {
            Arrays.fill(password, '0');
        }
        return null;
    }
}
