package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import model.*;

public class DrillingDAO {
    private static Connection con = DatabaseManager.getConnection();

    public static void insertDrillingService(Service service){
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
            JOptionPane.showMessageDialog(null,"Error Inserting Drilling Service " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static List<Service> getDrillingServicesByClient(int clientId){
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
}
