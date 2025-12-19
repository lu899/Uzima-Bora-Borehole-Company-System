package database;

import java.util.*;
import javax.swing.JOptionPane;
import java.sql.*;
import java.time.LocalDateTime;

import model.Admin;
import util.PasswordHasher;

public class AdminDAO {
    List<Admin> admins = new ArrayList<>();
    Connection con = DatabaseManager.getConnection();

    public void insertAdmin(Admin admin, char[] password){
        String insertSQL = "INSERT INTO admins(email, password, full_name, is_active) VALUES (?,?,?,?)";

        try (PreparedStatement pst = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            String hashedPassword = PasswordHasher.hashPassword(password);
            pst.setString(1, admin.getUserEmail());
            pst.setString(2, hashedPassword);
            pst.setString(3, admin.getUsername());
            pst.setBoolean(4, false);
            pst.executeUpdate();

            try(ResultSet rs = pst.getGeneratedKeys()){
                if (rs.next()) {
                    admin.setUserId(rs.getInt(1));
                    admin.setPassword(hashedPassword);
                    admin.setIsActive(false);
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

    public Admin getAdmin(String email, char[] password){
        String selectURL = "SELECT * FROM admins WHERE email LIKE ?";

        try (PreparedStatement pst = con.prepareStatement(selectURL)) {
            pst.setString(1, "%" + email + "%");
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                boolean isValid = PasswordHasher.verifyPassword(password, storedHash);
                if (isValid) {
                    Admin admin = new Admin(rs.getString("full_name"), rs.getString("email"));
                    admin.setPassword(storedHash);
                    admin.setIsActive(true);
                    admin.setLastLogin(rs.getTimestamp("last_login"));
                    return admin;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Password!", "Failure", JOptionPane.WARNING_MESSAGE);
                }  
            } else {
                JOptionPane.showMessageDialog(null, "User not found!", "Failure", JOptionPane.WARNING_MESSAGE);
            }
        } catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Login failed: " + e.getMessage(), "Failed", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Login failed: " + e.getMessage(), "Failed", JOptionPane.ERROR_MESSAGE);
        } finally {
            Arrays.fill(password, '0');
        }
        return null;
    }

    public List<Admin> getAllAdmins(){
        String selectSQL = "SELECT * FROM admins";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectSQL);

            while (rs.next()) {
                Admin admin = new Admin(rs.getString("full_name"), rs.getString("email"));
                admin.setIsActive(rs.getBoolean("is_active"));
                admin.setLastLogin(rs.getTimestamp("last_login"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error loding admins " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return admins;
    }

    public boolean deleteAdmin(int adminId){
        String deleteSQL = "DELETE FROM admins WHERE admin_id = ?";
        boolean success = false;
        try (PreparedStatement pst = con.prepareStatement(deleteSQL)) {
            pst.setInt(1, adminId);
            pst.executeUpdate();
            success = true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting admin " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return success;
    }

    public void updateAdminLastLogin(int adminId){
        String upadateSQL = "UPDATE admins SET last_login = ? WHERE admin_id = ?";

        try (PreparedStatement pst = con.prepareStatement(upadateSQL)) {
            pst.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            pst.setInt(2, adminId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public Timestamp getAdminLastLogin(int adminId){
        String selectURL = "SELECT last_login FROM admins WHERE admin_id = ?";

        try (PreparedStatement pst = con.prepareStatement(selectURL)) {
            pst.setInt(1, adminId);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
