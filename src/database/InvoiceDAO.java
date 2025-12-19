package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Invoice;
import model.Invoice.PaymentStatus;
import model.Client;

public class InvoiceDAO {
    Connection con = DatabaseManager.getConnection();
    private ClientDAO clientDAO = new ClientDAO();

    public List<Invoice> getRecentInvoices(){
        String selectSQL = "SELECT * FROM invoices ORDER BY invoice_id DESC LIMIT 3";
        List<Invoice> invoices = new ArrayList<>();

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectSQL);

            while (rs.next()) {
                Client client = clientDAO.getClientById(rs.getInt("client_id"));
                Invoice invoice = new Invoice(client);
                invoice.setInvoiceId(rs.getInt("invoice_id"));
                invoice.setServiceType(rs.getString("service_type"));
                invoice.setAmountPaid(rs.getDouble("amount_paid"));
                invoices.add(invoice);
            }
        } catch (SQLException e) {
           JOptionPane.showMessageDialog(null, e.getMessage());
           e.printStackTrace();
        }
        return invoices;
    }

    public double getTotalRevenue(){
        String selectSQL = "SELECT SUM(amount_paid) AS total FROM invoices;";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectSQL);

            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public Invoice getInvoice(int invoiceID, Client client){
        String selectSQL = "SELECT * FROM invoices WHERE invoice_id = ?";

        try (PreparedStatement pst = con.prepareStatement(selectSQL)) {
            pst.setInt(1, invoiceID);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Invoice invoice = new Invoice(client);
                invoice.setInvoiceId(invoiceID);
                invoice.setInvoiceNubmer(rs.getString("invoice_no"));
                invoice.setServiceType(rs.getString("service_type"));
                invoice.setAmountPaid(rs.getDouble("amount_paid"));
                invoice.setGrandTotal(rs.getDouble("total"));
                invoice.setStatus(PaymentStatus.valueOf(rs.getString("status").toUpperCase()));
                invoice.setInvoiceDate(rs.getDate("invoice_date").toLocalDate());

                return invoice;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Invoice> getInvoiceByClient(int clientID){
        List<Invoice> invoices = new ArrayList<>();
        String selectSQL = "SELECT * FROM invoices WHERE client_id = ?";

        try (PreparedStatement pst = con.prepareStatement(selectSQL)) {
            pst.setInt(1, clientID);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Client client = clientDAO.getClientById(rs.getInt("client_id"));
                Invoice invoice = new Invoice(client);
                invoice.setInvoiceId(rs.getInt("invoice_id"));
                invoice.setInvoiceNubmer(rs.getString("invoice_no"));
                invoice.setServiceType(rs.getString("service_type"));
                invoice.setAmountPaid(rs.getDouble("amount_paid"));
                invoice.setGrandTotal(rs.getDouble("total"));
                invoice.setStatus(PaymentStatus.valueOf(rs.getString("status").toUpperCase()));
                invoice.setInvoiceDate(rs.getDate("invoice_date").toLocalDate());
                invoices.add(invoice);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        return invoices;
    }
}