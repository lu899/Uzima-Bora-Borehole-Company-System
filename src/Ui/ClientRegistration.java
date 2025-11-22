package Ui;

import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

import model.Client;
import model.ClientCategory;
import util.InputValidator;
import database.ClientDAO;

public class ClientRegistration {
    private static Font fontAwesome;
    private static ClientDAO clientDAO = new ClientDAO();
    
    static {
        try {
            // Load Font Awesome font from resources
            InputStream is = ClientRegistration.class
                .getResourceAsStream("/Resources/Font Awesome 7 Free-Solid-900.otf");
            fontAwesome = Font.createFont(Font.TRUETYPE_FONT, is)
                .deriveFont(Font.PLAIN, 24f);
            GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
            ge.registerFont(fontAwesome);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void login(){
        JFrame loginFrame = new JFrame();
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(new Dimension(400, 400));
        loginFrame.setTitle("Uzima Bora Borehole System");

        List<Image> icons = new ArrayList<>();
        icons.add(new ImageIcon(ClientRegistration.class.getResource("/Resources/icon16X16.png")).getImage());
        icons.add(new ImageIcon(ClientRegistration.class.getResource("/Resources/icon32X32.png")).getImage());
        icons.add(new ImageIcon(ClientRegistration.class.getResource("/Resources/icon64X64.png")).getImage());

        loginFrame.setIconImages(icons);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel mainLabel = new JLabel("Login Form");
        mainLabel.setFont(new Font("Cambri", Font.BOLD, 25));
        mainLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setMinimumSize(new Dimension(250, 250));
        detailsPanel.setMaximumSize(new Dimension(300, 250));
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        detailsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        namePanel.setMaximumSize(new Dimension(220, 30));
        namePanel.setMinimumSize(new Dimension(220, 30));
        namePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel userIcon = new JLabel("\uf2bd");
        userIcon.setFont(fontAwesome);
        JLabel nameLabel = new JLabel(" Enter Your Name:");
        nameLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        namePanel.add(userIcon);
        namePanel.add(nameLabel);

        JTextField name = new JTextField(30);
        Dimension fixedSize = new Dimension(name.getPreferredSize().width, 30);
        name.setMinimumSize(fixedSize);
        name.setMaximumSize(fixedSize);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        passwordPanel.setMaximumSize(new Dimension(220, 30));
        passwordPanel.setMinimumSize(new Dimension(220, 30));
        passwordPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel passwordIcon = new JLabel("\uf084");
        passwordIcon.setFont(fontAwesome);
        JLabel passwordLabel = new JLabel(" Enter Your Password:");
        passwordLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        passwordPanel.add(passwordIcon);
        passwordPanel.add(passwordLabel);

        JPasswordField password = new JPasswordField(20);
        password.setMinimumSize(fixedSize);
        password.setMaximumSize(fixedSize);
        password.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedButton loginBtn = new RoundedButton("Log in", 20);
        loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setBackground(new Color(41, 74, 102));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.addActionListener(e -> {
            if (InputValidator.validateName(name) && InputValidator.validatePassword(password)) {
                Client c = clientDAO.getClient(name.getText(), password.getPassword());
                if (c != null) {
                    JOptionPane.showMessageDialog(loginFrame, "User logged in Successfully!!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Failed User log in!!", "Failed", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        JPanel registerPanel = new JPanel();
        registerPanel.setMinimumSize(new Dimension(300, 90));
        registerPanel.setMaximumSize(new Dimension(300, 90));
        registerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel registerLabel = new JLabel("Don't have an account? ");
        registerLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
        
        RoundedButton registerBtn = new RoundedButton("Register", 20);
        registerBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        registerBtn.setBackground(new Color(87,151,158));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.addActionListener(e -> {
            loginFrame.dispose();
            register();
        });

        registerPanel.add(registerLabel);
        registerPanel.add(Box.createHorizontalStrut(5));
        registerPanel.add(registerBtn);

        detailsPanel.add(namePanel);
        detailsPanel.add(Box.createVerticalStrut(8));
        detailsPanel.add(name);
        detailsPanel.add(Box.createVerticalStrut(25));
        detailsPanel.add(passwordPanel);
        detailsPanel.add(Box.createVerticalStrut(8));
        detailsPanel.add(password);

        mainPanel.add(mainLabel);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(detailsPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(loginBtn);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(registerPanel);

        loginFrame.getContentPane().add(mainPanel);
        loginFrame.setVisible(true);
        loginFrame.setResizable(false); 
        loginFrame.setLocationRelativeTo(null);
    }

    public static void register(){
        JFrame registerFrame = new JFrame();
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerFrame.setSize(new Dimension(450, 600));
        registerFrame.setTitle("Uzima Bora Borehole System");

        List<Image> icons = new ArrayList<>();
        icons.add(new ImageIcon(ClientRegistration.class.getResource("/Resources/icon16X16.png")).getImage());
        icons.add(new ImageIcon(ClientRegistration.class.getResource("/Resources/icon32X32.png")).getImage());
        icons.add(new ImageIcon(ClientRegistration.class.getResource("/Resources/icon64.png")).getImage());

        registerFrame.setIconImages(icons);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JLabel mainLabel = new JLabel("Registration Form");
        mainLabel.setFont(new Font("Cambri", Font.BOLD, 25));
        mainLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setMinimumSize(new Dimension(370, 1300));
        detailsPanel.setMaximumSize(new Dimension(370, 1300));
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));
        detailsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        namePanel.setMaximumSize(new Dimension(220, 30));
        namePanel.setMinimumSize(new Dimension(220, 30));
        namePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel userIcon = new JLabel("\uf2bd");
        userIcon.setFont(fontAwesome);
        JLabel nameLabel = new JLabel(" Enter Your Name:");
        nameLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        namePanel.add(userIcon);
        namePanel.add(nameLabel);

        JTextField name = new JTextField(30);
        Dimension fixedSize = new Dimension(220, 30);
        name.setPreferredSize(fixedSize);
        name.setMinimumSize(fixedSize);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        emailPanel.setMaximumSize(new Dimension(220, 30));
        emailPanel.setMinimumSize(new Dimension(220, 30));
        emailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel emailIcon = new JLabel("\uf27a");
        emailIcon.setFont(fontAwesome);
        JLabel emailLabel = new JLabel(" Enter Your Email:");
        emailLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        emailPanel.add(emailIcon);
        emailPanel.add(emailLabel);

        JTextField email = new JTextField(30);
        email.setPreferredSize(fixedSize);
        email.setMinimumSize(fixedSize);
        email.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        passwordPanel.setMaximumSize(new Dimension(220, 30));
        passwordPanel.setMinimumSize(new Dimension(220, 30));
        passwordPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel passwordIcon = new JLabel("\uf084");
        passwordIcon.setFont(fontAwesome);
        JLabel passwordLabel = new JLabel(" Enter Your Password:");
        passwordLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        passwordPanel.add(passwordIcon);
        passwordPanel.add(passwordLabel);

        JPasswordField password = new JPasswordField(20);
        password.setPreferredSize(fixedSize);
        password.setMinimumSize(fixedSize);
        password.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel strengthLabel = new JLabel();
        strengthLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));

        JPanel confirmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        confirmPanel.setMaximumSize(new Dimension(300, 30));
        confirmPanel.setMinimumSize(new Dimension(300, 30));
        confirmPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel confirmIcon = new JLabel("\uf09c");
        confirmIcon.setFont(fontAwesome);
        JLabel confirmLabel = new JLabel(" Confirm Your Password:");
        confirmLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        confirmPanel.add(confirmIcon);
        confirmPanel.add(confirmLabel);

        JPasswordField confirm = new JPasswordField(20);
        confirm.setPreferredSize(fixedSize);
        confirm.setMinimumSize(fixedSize);
        confirm.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel matchLabel = new JLabel();
        matchLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));

        Runnable checkPasswordMatch = () -> {
            char[] pass = password.getPassword();
            char[] confirmPassword = confirm.getPassword();

            if (confirmPassword.length == 0) {
                matchLabel.setText("");
                return;
            }

            if (InputValidator.PasswordValidator.passwordsMatch(pass, confirmPassword)) {
                matchLabel.setText("Passwords Match: ✓ Match");
                matchLabel.setForeground(Color.GREEN);
            } else {
                matchLabel.setText("Passwords Match: ✗ No Match");
                matchLabel.setForeground(Color.RED);
            }
        };

        password.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }

            public void update(){
                char[] pass = password.getPassword();
                String strength = InputValidator.PasswordValidator.getStrengthRating(pass);
                strengthLabel.setText("Password Strength: " + strength);

                if (strength.equals("Strong")) {
                    strengthLabel.setForeground(Color.GREEN);
                } else if (strength.equals("Medium")) {
                    strengthLabel.setForeground(Color.ORANGE);
                } else {
                    strengthLabel.setForeground(Color.RED);
                }

                checkPasswordMatch.run();
            }
        });

        confirm.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { checkPasswordMatch.run(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { checkPasswordMatch.run(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { checkPasswordMatch.run(); }
        });

        JPanel telephonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        telephonePanel.setMaximumSize(new Dimension(300, 30));
        telephonePanel.setMinimumSize(new Dimension(300, 30));
        telephonePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel telephoneIcon = new JLabel("\uf2a0");
        telephoneIcon.setFont(fontAwesome);
        JLabel telephoneLabel = new JLabel(" Enter Phone Number:");
        telephoneLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        telephonePanel.add(telephoneIcon);
        telephonePanel.add(telephoneLabel);

        JTextField telephone = new JTextField(30);
        telephone.setPreferredSize(fixedSize);
        telephone.setMinimumSize(fixedSize);
        telephone.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        addressPanel.setMaximumSize(new Dimension(220, 30));
        addressPanel.setMinimumSize(new Dimension(220, 30));
        addressPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel addressIcon = new JLabel("\uf3c5");
        addressIcon.setFont(fontAwesome);
        JLabel addressLabel = new JLabel(" Enter your Address:");
        addressLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        addressPanel.add(addressIcon);
        addressPanel.add(addressLabel);

        JTextField address = new JTextField(30);
        address.setPreferredSize(fixedSize);
        address.setMinimumSize(fixedSize);
        address.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel locationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        locationPanel.setMaximumSize(new Dimension(300, 30));
        locationPanel.setMinimumSize(new Dimension(300, 30));
        locationPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel locationIcon = new JLabel("\uf08d");
        locationIcon.setFont(fontAwesome);
        JLabel locationLabel = new JLabel(" Enter Borehole location:");
        locationLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        locationPanel.add(locationIcon);
        locationPanel.add(locationLabel);

        JTextField location = new JTextField(30);
        location.setPreferredSize(fixedSize);
        location.setMinimumSize(fixedSize);
        location.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        categoryPanel.setMaximumSize(new Dimension(300, 30));
        categoryPanel.setMinimumSize(new Dimension(300, 30));
        categoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel categoryIcon = new JLabel("\uf007");
        categoryIcon.setFont(fontAwesome);
        JLabel categoryLabel = new JLabel(" Select Client Category:");
        categoryLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        ClientCategory[] options = {ClientCategory.COMMERCIAL, ClientCategory.DOMESTIC, ClientCategory.INDUSTRIAL};
        JComboBox<ClientCategory> category = new JComboBox<>(options);
        category.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        category.setMinimumSize(fixedSize);
        category.setFont(new Font("Cambria", Font.PLAIN, 16));
        category.setAlignmentX(Component.LEFT_ALIGNMENT);
        category.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        categoryPanel.add(categoryIcon);
        categoryPanel.add(categoryLabel);
        
        RoundedButton registerBtn = new RoundedButton("Register", 20);
        registerBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        registerBtn.setBackground(new Color(87,151,158));
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerBtn.addActionListener(e -> {
            if (InputValidator.validateAll(name, email, telephone, password, confirm)) {
                Client client = new Client(name.getText(), 
                                           address.getText(), 
                                           telephone.getText(), 
                                           email.getText(), 
                                           location.getText(), 
                                           (ClientCategory) category.getSelectedItem());
                clientDAO.insertClient(client, password.getPassword());
                registerFrame.dispose();
            }
        });

        detailsPanel.add(namePanel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(name);
        detailsPanel.add(Box.createVerticalStrut(20));
        detailsPanel.add(emailPanel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(email);
        detailsPanel.add(Box.createVerticalStrut(20));
        detailsPanel.add(telephonePanel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(telephone);
        detailsPanel.add(Box.createVerticalStrut(20));
        detailsPanel.add(passwordPanel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(password);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(strengthLabel);
        detailsPanel.add(Box.createVerticalStrut(20));
        detailsPanel.add(confirmPanel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(confirm);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(matchLabel);
        detailsPanel.add(Box.createVerticalStrut(20));
        detailsPanel.add(addressPanel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(address);
        detailsPanel.add(Box.createVerticalStrut(20));
        detailsPanel.add(locationPanel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(location);
        detailsPanel.add(Box.createVerticalStrut(20));
        detailsPanel.add(categoryPanel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(category);        

        mainPanel.add(mainLabel);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(detailsPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(registerBtn);
        mainPanel.add(Box.createVerticalStrut(10));

        registerFrame.getContentPane().add(scrollPane);
        registerFrame.setVisible(true);
        registerFrame.setLocationRelativeTo(null);
    }
}
