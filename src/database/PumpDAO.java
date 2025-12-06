package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import model.*;

public class PumpDAO {
    private static Connection con = DatabaseManager.getConnection();

    public static void insertPumpService(Service service){
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
            JOptionPane.showMessageDialog(null,"Error Inserting Pump Installation Service " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

     public static List<Service> getPumpInstallationByClient(int clientId){
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
}
