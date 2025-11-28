package util;
import javax.swing.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.*;

public class InputValidator {
    public static class TextValidator {
        public static String getNameErrorMessage(String name) {
            if (name == null || name.trim().isEmpty()) {
                return "Name cannot be empty";
            }
            if (name.trim().length() < 2) {
                return "Name must be at least 2 characters long";
            }
            return null;
        }

        public static String getAddressErrorMessage(String address) {
            if (address == null || address.trim().isEmpty()) {
                return "Address cannot be empty";
            }
            if (address.trim().length() < 2) {
                return "Address must be at least 2 characters long";
            }
            return null;
        }

        public static String getLocationErrorMessage(String location) {
            if (location == null || location.trim().isEmpty()) {
                return "Borehole Location cannot be empty";
            }
            if (location.trim().length() < 2) {
                return "Borehole Location must be at least 2 characters long";
            }
            return null;
        }
    }

    public static class EmailValidator{
        private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

        public static boolean isValid(String email){
            if (email == null || email.trim().isEmpty()) {
                return false;
            }

            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }

        public static String getErrorMessage(String email){
            if (email == null || email.trim().isEmpty()) {
                return "Email cannot be empty";
            }
            if (!isValid(email)) {
                return "Invalid email format. Example: user@example.com";
            }
            return null;
        }
    }

    public static class PhoneValidator{
        public static String getErrorMessage(String phone){
            if (phone == null || phone.trim().isEmpty()) {
                return "Phone number cannot be empty";
            }
            String cleaned = phone.replaceAll("[\\s\\-()]", "");
            
            if (!cleaned.matches("[0-9]+")) {
                return "Phone number must contain only digits";
            }
            
            if (cleaned.length() != 10) {
                return "Phone number must be exactly 10 digits (you entered " + cleaned.length() + ")";
            }
            return null;
        }
    }

    public static class PasswordValidator {
        private static final int MIN_LENGTH = 8;

        public static boolean passwordsMatch(char[] password, char[] confirmPassword){
            if (password == null || confirmPassword == null) {
                return false;
            }

            if (password.length != confirmPassword.length) {
                return false;
            }

            for (int i=0; i<password.length; i++) {
                if(password[i] != confirmPassword[i]){
                    return false;
                }
            }
            return true;
        }

        public static String getErrorMessage(char[] password){
           if (password == null || password.length == 0) {
                return "Password cannot be empty";
            }
            
            String pass = new String(password);
            StringBuilder errors = new StringBuilder("Password must contain:\n");
            boolean hasError = false;
            
            if (pass.length() < MIN_LENGTH) {
                errors.append("- At least ").append(MIN_LENGTH).append(" characters\n");
                hasError = true;
            }
            
            if (!pass.matches(".*[A-Z].*")) {
                errors.append("- At least one uppercase letter (A-Z)\n");
                hasError = true;
            }
            
            if (!pass.matches(".*[a-z].*")) {
                errors.append("- At least one lowercase letter (a-z)\n");
                hasError = true;
            }
            
            if (!pass.matches(".*[0-9].*")) {
                errors.append("- At least one digit (0-9)\n");
                hasError = true;
            }
            
            if (!pass.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
                errors.append("- At least one special character (!@#$%^&* etc.)\n");
                hasError = true;
            }
            
            return hasError ? errors.toString() : null;
        }

        public static String getStrengthRating(char[] password) {
            if (password == null || password.length == 0) {
                return "No password";
            }
            
            String pass = new String(password);
            int strength = 0;
            
            if (pass.length() >= 8) strength++;
            if (pass.length() >= 12) strength++;
            if (pass.matches(".*[A-Z].*")) strength++;
            if (pass.matches(".*[a-z].*")) strength++;
            if (pass.matches(".*[0-9].*")) strength++;
            if (pass.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) strength++;
            
            if (strength <= 2) return "Weak";
            if (strength <= 4) return "Medium";
            return "Strong";
        }
    }

    public static boolean validateEmail(JTextField emailField){
        String email = emailField.getText();
        String error = EmailValidator.getErrorMessage(email);

        if (error != null) {
            JOptionPane.showMessageDialog(null, error, "Email Validation Error", JOptionPane.ERROR_MESSAGE);
            emailField.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean validateName(JTextField nameField) {
        String name = nameField.getText();
        String error = TextValidator.getNameErrorMessage(name);

        if (error != null) {
            JOptionPane.showMessageDialog(null, error, "Name Validation Error", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean validateAddress(JTextField addressField) {
        String address = addressField.getText();
        String error = TextValidator.getAddressErrorMessage(address);

        if (error != null) {
            JOptionPane.showMessageDialog(null, error, "Address Validation Error", JOptionPane.ERROR_MESSAGE);
            addressField.requestFocus();
            return false;
        }
        return true;
    }
    public static boolean validateLocation(JTextField locationField) {
        String location = locationField.getText();
        String error = TextValidator.getLocationErrorMessage(location);

        if (error != null) {
            JOptionPane.showMessageDialog(null, error, "Borehole Location Validation Error", JOptionPane.ERROR_MESSAGE);
            locationField.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean validatePhone(JTextField phoneField){
        String phone = phoneField.getText();
        String error = PhoneValidator.getErrorMessage(phone);

        if (error != null) {
            JOptionPane.showMessageDialog(null, error, "Phone Validation Error", JOptionPane.ERROR_MESSAGE);
            phoneField.requestFocus();
            return false;
        }
        return true;
    }  

    public static boolean validatePassword(JPasswordField passwordField){
        char[] password = passwordField.getPassword();
        String error = PasswordValidator.getErrorMessage(password);

        if (error != null) {
            JOptionPane.showMessageDialog(null, error, "Password Validation Error", JOptionPane.ERROR_MESSAGE);
            passwordField.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean validatePasswordMatch(JPasswordField passwordField, JPasswordField confirmPasswordField){
        char[] password = passwordField.getPassword();
        char[] confirmPassword = confirmPasswordField.getPassword();

        if (!PasswordValidator.passwordsMatch(password, confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match. Please try again.", "Password Mismatch", JOptionPane.ERROR_MESSAGE);
            confirmPasswordField.requestFocus();
            return false;
        }
        return true;
    }

     public static String formatLastLoginForDisplay(Timestamp lastLogin) {
        if (lastLogin == null) {
            return "First login";
        }
        
        LocalDateTime dateTime = lastLogin.toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();
        
        // Calculate time difference
        long minutesAgo = java.time.Duration.between(dateTime, now).toMinutes();
        long hoursAgo = minutesAgo / 60;
        long daysAgo = hoursAgo / 24;
        
        // Format based on how long ago
        if (minutesAgo < 1) {
            return "Just now";
        } else if (minutesAgo < 60) {
            return minutesAgo + " minutes ago";
        } else if (hoursAgo < 24) {
            return hoursAgo + " hours ago";
        } else if (daysAgo < 7) {
            return daysAgo + " days ago";
        } else {
            // Show full date
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
            return dateTime.format(formatter);
        }
    }

    public static boolean validateAll(JTextField nameField,
                                      JTextField emailField,
                                      JTextField phoneField,
                                      JPasswordField passwordField, 
                                      JPasswordField confirmPasswordField){
        return validateName(nameField) &&
               validateEmail(emailField) && 
               validatePhone(phoneField) && 
               validatePassword(passwordField) &&
               validatePasswordMatch(passwordField, confirmPasswordField);
    }
}
