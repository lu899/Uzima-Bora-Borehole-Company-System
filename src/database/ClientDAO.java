package database;

import java.sql.*;
import java.time.LocalDateTime;
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

    public Client getClient(String email, char[] password){
        String selectSQL = "SELECT * FROM clients WHERE email LIKE ?";
        try (PreparedStatement pst = con.prepareStatement(selectSQL)) {
            pst.setString(1, "%" + email + "%");

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
                    user.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
                    user.setLastLogin(rs.getTimestamp("last_login"));
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

    public Client getClientById(int id){
        String selectSQL = "SELECT * FROM clients WHERE client_id = ?";

        try (PreparedStatement pst = con.prepareStatement(selectSQL)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                ClientCategory category = ClientCategory.valueOf(rs.getString("client_category").toUpperCase());
                Client user = new Client(rs.getString("name"), 
                                        rs.getString("address"), 
                                        rs.getString("telephone"), 
                                        rs.getString("email"), 
                                        rs.getString("borehole_location"),
                                        category
                                    );
                return user;                   
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "User not found!", "Failure", JOptionPane.WARNING_MESSAGE);
        }
        return null;
    }
    
    public List<Client> getClients(){
        String selectSQL = "SELECT * FROM clients";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectSQL);

            while (rs.next()) {
                ClientCategory category = ClientCategory.valueOf(rs.getString("client_category").toUpperCase());
                Client client = new Client(rs.getString("name"), 
                                           rs.getString("address"), 
                                           rs.getString("telephone"), 
                                           rs.getString("email"), 
                                           rs.getString("borehole_location"),
                                           category
                                        );
                client.setClientId(rs.getInt("client_id"));
                client.setPassword(rs.getString("password"));
                client.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
                clients.add(client);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loding clients " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return clients;
    }

    public int countClients(){
        String countSQL = "SELECT COUNT(*) AS total FROM clients";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(countSQL);

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public boolean deleteClient(int clientId){
        boolean success = false;
        String deleteSQL = "DELETE FROM clients WHERE client_id = ?";

        try (PreparedStatement pst = con.prepareStatement(deleteSQL)) {
            pst.setInt(1, clientId);
            pst.executeUpdate();
            success = true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting client " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }

    public List<Service> getServicesByClient(int clientId){
        List<Service> allServices = new ArrayList<>();

        allServices.addAll(DrillingDAO.getDrillingServicesByClient(clientId));
        allServices.addAll(PumpDAO.getPumpInstallationByClient(clientId));
        allServices.addAll(TankDAO.getTankInstallationByClient(clientId));
        allServices.addAll(PlumbingDAO.getPlumbingServicesByClient(clientId));

        return allServices;
    }

    public void updateClientLastLogin(int clientId){
        String sql = "UPDATE clients SET last_login = ? WHERE client_id = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            pst.setInt(2, clientId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Timestamp getClientLastLogin(int clientId){
        String sql = "SELECT last_login FROM clients WHERE client_id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getTimestamp("last_login");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error getting client's Last Login: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }
}
