package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import database.DatabaseManager;
import model.Invoice;
import model.Invoice.PaymentStatus;

public class PaymentValidator {
    public static boolean inputValidation(double amount, double balance){
        if (amount <= 0) {
            JOptionPane.showMessageDialog(null, "Please enter a valid payment amount", "INVALID AMOUNT", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (amount > balance) {
            JOptionPane.showMessageDialog(null, "Payment cannot exceed outstanding balance", "OVERPAYMENT", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    public static void recordPayment(double amount, Invoice invoice){
        double newAmount = amount + invoice.getAmountPaid();
        double outstanding = invoice.getGrandTotal() - newAmount;

        if (outstanding <= 0 ) {
           invoice.setStatus(PaymentStatus.PAID);
            invoice.setAmountPaid(invoice.getGrandTotal());
        } else if(outstanding < invoice.getGrandTotal()){
            invoice.setStatus(PaymentStatus.PARTIAL);
            invoice.setAmountPaid(newAmount);
        } else {
            invoice.setStatus(PaymentStatus.UNPAID);
            invoice.setAmountPaid(newAmount);
        }

        Connection con = DatabaseManager.getConnection();
        String updateSQL = """
                UPDATE invoices
                SET amount_paid = ?,
                    status = ?
                WHERE invoice_id = ?
                """;
        try (PreparedStatement pst = con.prepareStatement(updateSQL)) {
            pst.setDouble(1, invoice.getAmountPaid());
            pst.setString(2, invoice.getPaymentStatus().getDisplayName());
            pst.setInt(3, invoice.getInvoiceId());
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Payment Recorded Successfully!! \n New Payment Status: " + invoice.getPaymentStatus().getDisplayName());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
    }
}
