package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import model.*;

public class TankDAO {
    private static Connection con = DatabaseManager.getConnection();

    public static void insertTankService(Service service){ 
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
            JOptionPane.showMessageDialog(null,"Error Inserting Tank Installation Service " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static List<Service> getTankInstallationByClient(int clientId){
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
    
    public static int countTankInstallations(){
        String selectSQL = "SELECT COUNT(*) AS total FROM tank_installation WHERE month(installation_date) = month(curdate())";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectSQL);
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}
