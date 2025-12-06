package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import model.*;

public class PlumbingDAO {
    private static Connection con = DatabaseManager.getConnection();

    public static void insertPlumbingService(Service service){
        String insertSQL = "INSERT INTO plumbing_service(client_id, pipe_type, pipe_diameter, pipe_length, outlets, total_cost, service_date) VALUES(?,?,?,?,?,?,?)";
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
            JOptionPane.showMessageDialog(null,"Error Inserting Plumbing Service " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static List<Service> getPlumbingServicesByClient(int clientId){
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
}
