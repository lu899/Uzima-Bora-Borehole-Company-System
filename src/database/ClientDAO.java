package database;

import java.sql.*;
import java.sql.Date;
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

    public void insertDrillingService(Service service){
        String insertSQL = "INSERT INTO drilling_services(client_id, drilling_type, borehole_depth, down_payment, depth_charges, total_cost, service_date) VALUES(?,?,?,?,?,?,?)";
        DrillingService drillingService = (DrillingService) service;

        try (PreparedStatement pst = con.prepareStatement(insertSQL)) {
            pst.setInt(1, drillingService.getClientId());
            pst.setString(2, drillingService.getDrilingType().name());
            pst.setDouble(3, drillingService.getBoreholeDepth());
            pst.setDouble(4, drillingService.getDownPayment());
            pst.setDouble(5, drillingService.getDepthCharges());
            pst.setDouble(6, drillingService.getTotalCost());
            pst.setDate(7, Date.valueOf(drillingService.getServiceDate()));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Drilling Service added Successfully!!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error Inserting Service " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insertPlumbingService(Service service){
        String insertSQL = "INSERT INTO plumbing_services(client_id, pipe_type, pipe_diameter, pipe_length, outlets, total_cost, service_date) VALUES(?,?,?,?,?,?,?)";
        PlumbingService plumbingService = (PlumbingService) service;

        try (PreparedStatement pst = con.prepareStatement(insertSQL)) {
            pst.setInt(1, plumbingService.getClientId());
            pst.setString(2, plumbingService.getPipeType().name());
            pst.setDouble(3, plumbingService.getPipeDiameter());
            pst.setDouble(4, plumbingService.getPipeLength());
            pst.setDouble(5, plumbingService.getNumOfOutlets());
            pst.setDouble(6, plumbingService.getTotalCost());
            pst.setDate(7, Date.valueOf(plumbingService.getServiceDate()));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Plumbing Service added Successfully!!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error Inserting Service " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insertPumpService(Service service){
        String insertSQL = "INSERT INTO pump_installation(client_id, pump_type, pump_cost, borehole_depth, tank_height, depth_cost, height_cost,total_cost, installation_date) VALUES(?,?,?,?,?,?,?,?,?)";
        PumpInstallation pumpInstallation = (PumpInstallation) service;

        try (PreparedStatement pst = con.prepareStatement(insertSQL)) {
            pst.setInt(1, pumpInstallation.getClientId());
            pst.setString(2, pumpInstallation.getPumpType().name());
            pst.setDouble(3, pumpInstallation.getBaseCost());
            pst.setDouble(4, pumpInstallation.getBoreholeDepth());
            pst.setDouble(5, pumpInstallation.getTankHeight());
            pst.setDouble(6, pumpInstallation.calculateDepthCharges());
            pst.setDouble(7, pumpInstallation.calculateHeightCharges());
            pst.setDouble(8, pumpInstallation.getTotalCost());
            pst.setDate(9, Date.valueOf(pumpInstallation.getServiceDate()));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Pump Installation Service added Successfully!!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error Inserting Service " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void insertTankService(Service service){
        String insertSQL = "INSERT INTO tank_installation(client_id, capacity_litres, total_cost, installation_date) VALUES(?,?,?,?)";
        TankInstallation tankInstallation = (TankInstallation) service;

        try (PreparedStatement pst = con.prepareStatement(insertSQL)) {
            pst.setInt(1, tankInstallation.getClientId());
            pst.setDouble(2, tankInstallation.getCapacity());
            pst.setDouble(3, tankInstallation.getTotalCost());
            pst.setDate(4, Date.valueOf(tankInstallation.getServiceDate()));
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tank Installation Service added Successfully!!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error Inserting Service " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Service> getDrillingServicesByClient(int clientId){
        List<Service> drillingServices = new ArrayList<>();
        String selectSQL = "SELECT * FROM drilling_services WHERE client_id = ?";
        try (PreparedStatement pst = con.prepareStatement(selectSQL)) {
            pst.setInt(1, clientId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                DrilingType dt = DrilingType.valueOf(rs.getString("drilling_type").toUpperCase());
                DrillingService ds = new DrillingService(rs.getInt("client_id"),
                                                         dt,
                                                         rs.getDouble("borehole_depth"),
                                                         rs.getDate("service_date").toLocalDate()
                                                        );
                drillingServices.add(ds);              
            }
        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null,"Error getting Drilling Services " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return drillingServices;
    }

    public List<Service> getPlumbingServicesByClient(int clientId){
        List<Service> plumbingServices = new ArrayList<>();
        String selectSQL = "SELECT * FROM plumbing_services WHERE client_id = ?";
        try (PreparedStatement pst = con.prepareStatement(selectSQL)) {
            pst.setInt(1, clientId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                PipeType pt = PipeType.valueOf(rs.getString("pipe_type").toUpperCase());
                PlumbingService ds = new PlumbingService(rs.getInt("client_id"),
                                                         pt,                                                    
                                                         rs.getDouble("pipe_diameter"),
                                                         rs.getDouble("pipe_length"),
                                                         rs.getInt("outlets"),
                                                         rs.getDate("service_date").toLocalDate()
                                                        );
                plumbingServices.add(ds);
            }
        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null,"Error getting plumbing Services " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return plumbingServices;
    }

    public List<Service> getPumpInstallationByClient(int clientId){
        List<Service> pumpInstallation = new ArrayList<>();
        String selectSQL = "SELECT * FROM pump_installation WHERE client_id = ?";
        try (PreparedStatement pst = con.prepareStatement(selectSQL)) {
            pst.setInt(1, clientId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                PumpType pt = PumpType.valueOf(rs.getString("pump_type").toUpperCase());
                PumpInstallation ds = new PumpInstallation(rs.getInt("client_id"),
                                                           pt,                                                    
                                                           rs.getDouble("borehole_depth"),
                                                           rs.getDouble("tank_height"),
                                                           rs.getDate("service_date").toLocalDate()
                                                        );
                pumpInstallation.add(ds);
            }
        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null,"Error getting Pump Installation Services " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return pumpInstallation;
    }

    public List<Service> getTankInstallationByClient(int clientId){
        List<Service> tankInstallation = new ArrayList<>();
        String selectSQL = "SELECT * FROM tank_installation WHERE client_id = ?";
        try (PreparedStatement pst = con.prepareStatement(selectSQL)) {
            pst.setInt(1, clientId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                TankInstallation ds = new TankInstallation(clientId, rs.getDouble("capacity_litres"), rs.getDate("installation_date").toLocalDate());
                tankInstallation.add(ds);
            }
        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null,"Error getting Tank Installation Service " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return tankInstallation;
    }

    public List<Service> getServicesByClient(int clientId){
        List<Service> allServices = new ArrayList<>();

        allServices.addAll(getDrillingServicesByClient(clientId));
        allServices.addAll(getPumpInstallationByClient(clientId));
        allServices.addAll(getTankInstallationByClient(clientId));
        allServices.addAll(getPlumbingServicesByClient(clientId));

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
