package database;

import java.sql.*;
import javax.swing.*;
import java.util.*;

import model.*;

public class ApplicationDAO {
    private static Connection con = DatabaseManager.getConnection();

    public static boolean insertApplication(Application app){
        String insertSQL = """
                INSERT INTO applications(application_no, client_id, full_name, address, telephone, email, client_category, borehole_location, needs_drilling, drilling_type, estimated_depth, needs_pump, pump_type, tank_height, needs_tank, tank_capacity, needs_plumbing, pipe_type, pipe_diameter, pipe_length, number_of_outlets, additional_notes, estimated_cost, status, submitted_date) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
                """;
        boolean successfull = false;
        
        try (PreparedStatement pst = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, app.getApplicationNumber());
            pst.setInt(2, app.getClient().getClientId());
            pst.setString(3, app.getClient().getUsername());
            pst.setString(4, app.getClient().getClientAddress());
            pst.setString(5, app.getClient().getClientTelephone());
            pst.setString(6, app.getClient().getUserEmail());
            pst.setString(7, app.getClient().getClientCategory().name());
            pst.setString(8, app.getBoreholeLocation());
            pst.setBoolean(9, app.isNeedsDrilling());
            if(app.isNeedsDrilling()){
                pst.setString(10, app.getDrillingType().name());
                pst.setDouble(11, app.getEstimatedDepth());
            } else {
                pst.setNull(10, Types.VARCHAR);
                pst.setNull(11, Types.DOUBLE);
            }
            
            pst.setBoolean(12, app.isNeedsPump());
            if (app.isNeedsPump()) {
                pst.setString(13, app.getPumpType().name());
                pst.setDouble(14, app.getTankHeight());
            } else {
                pst.setNull(13, Types.VARCHAR);
                pst.setNull(14, Types.DOUBLE);
            }

            pst.setBoolean(15, app.isNeedsTank());
            if (app.isNeedsPump()) {
                pst.setDouble(16, app.getTankCapacity());
            } else {
                pst.setNull(16, Types.DOUBLE);
            }

            pst.setBoolean(17, app.isNeedsPlumbing());
            if (app.isNeedsPlumbing()) {
                pst.setString(18, app.getPipeType().name());
                pst.setDouble(19, app.getPipeDiameter());
                pst.setDouble(20, app.getPipeLength());
                pst.setInt(21, app.getNumberOfOutlets());
            } else {
                pst.setNull(18, Types.VARCHAR);
                pst.setNull(19, Types.DOUBLE);
                pst.setNull(20, Types.DOUBLE);
                pst.setNull(21, Types.INTEGER);
            }

            pst.setString(22, app.getAdditionalNotes());
            pst.setDouble(23, app.getEstimatedCost());
            pst.setString(24, app.getStatus().name());
            pst.setTimestamp(25, app.getSubmittedDate());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int applicationId = rs.getInt(1);
                    app.setApplicationId(applicationId);
                    successfull = true;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Couldn't insert application: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return successfull;
    }

    public static List<Application> getApplicationBy(Client client){
        List<Application> applications = new ArrayList<>();
        String selectSQL = "SELECT * FROM applications WHERE client_id = ?";

        try (PreparedStatement pst = con.prepareStatement(selectSQL)) {
            pst.setInt(1, client.getClientId());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Application app = new Application(client);
                app.setapplicationNumber(rs.getString("application_no"));
                app.setSubmittedDate(rs.getTimestamp("submitted_date"));
                app.setNeedsDrilling(rs.getBoolean("needs_drilling"));
                app.setNeedsPlumbing(rs.getBoolean("needs_plumbing"));
                app.setNeedsPump(rs.getBoolean("needs_pump"));
                app.setNeedsTank(rs.getBoolean("needs_tank"));
                app.setTotal(rs.getDouble("estimated_cost"));
                app.setStatus(Status.valueOf(rs.getString("status").toUpperCase()));
                applications.add(app);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        return applications;
    }
}
