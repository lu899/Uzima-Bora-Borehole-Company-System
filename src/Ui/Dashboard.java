package Ui;

import javax.swing.*;
import database.ClientDAO;
import model.*;
import util.InputValidator;

import java.util.*;
import java.util.List;
import java.awt.*;
import java.io.*;

public class Dashboard {
    private static Font fontAwesome;
    private static JFrame landingFrame;
    private static ClientDAO clientDAO = new ClientDAO();
    private static Client client;

    static {
        try {
            // Load Font Awesome font from resources
            InputStream is = Dashboard.class
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
    public static void landingPage() {
        landingFrame = new JFrame("Uzima Bora Borehole System");
        landingFrame.setSize(new Dimension(900, 650));
        landingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        landingFrame.setLayout(new BorderLayout());

        List<Image> icons = new ArrayList<>();
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon16X16.png")).getImage());
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon32X32.png")).getImage());
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon64X64.png")).getImage());

        landingFrame.setIconImages(icons);

        // Navigation Bar

        JPanel navPanel = new JPanel();
        navPanel.setPreferredSize(new Dimension(900, 80));
        navPanel.setBackground(new Color(44, 62, 80));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        logoPanel.setBackground(new Color(44, 62, 80));

        ImageIcon imageIcon = new ImageIcon(Dashboard.class.getResource("/Resources/icon64X64.png"));
        JLabel imageLabel = new JLabel(imageIcon);

        JLabel titleLabel = new JLabel("UZIMA BOREHOLE DRILLING SERVICE");
        titleLabel.setFont(new Font("Cambria", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        logoPanel.add(imageLabel);
        logoPanel.add(titleLabel);

        navPanel.add(logoPanel, BorderLayout.WEST);

        // Main Content Area

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        mainPanel.setBackground(new Color(236, 240, 241));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(new Color(236, 240, 241));
        welcomePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Kenya's Premier Borehole Solutions");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Welcome! Please select your Portal");
        welcomeLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(44, 62, 80));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

        welcomePanel.add(subtitleLabel);
        welcomePanel.add(Box.createVerticalStrut(10));
        welcomePanel.add(welcomeLabel);

        mainPanel.add(welcomePanel);

        // Card Panel

        JPanel cardPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        cardPanel.setBackground(new Color(236, 240, 241));
        cardPanel.setMaximumSize(new Dimension(750, 300));
        cardPanel.setPreferredSize(new Dimension(750, 300));

        // Client Card

        JPanel clientCard = new JPanel();
        clientCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(30, 20, 30, 20)
        ));
        clientCard.setLayout(new BoxLayout(clientCard, BoxLayout.Y_AXIS));
        clientCard.setBackground(Color.white);
        clientCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel clientIcon = new JLabel("\uf007", SwingConstants.CENTER);
        clientIcon.setFont(fontAwesome.deriveFont(Font.PLAIN, 48f));
        clientIcon.setForeground(new Color(52, 152, 219));
        clientIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel clientLabel = new JLabel("CLIENT LOGIN");
        clientLabel.setFont(new Font("Cambria", Font.BOLD, 22));
        clientLabel.setForeground(new Color(44, 62, 80));
        clientLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        clientLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JLabel clientDesc = new JLabel("<html><center>For customers who want to:</center></html>");
        clientDesc.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        clientDesc.setForeground(new Color(127, 140, 141));
        clientDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel featuresPanel = new JPanel();
        featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.Y_AXIS));
        featuresPanel.setBackground(Color.WHITE);
        featuresPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        String[] features = {"• Apply for services", "• Track applications", "• View invoices"};
        for (String feature : features) {
            JLabel featureLabel = new JLabel(feature);
            featureLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            featureLabel.setForeground(new Color(52, 73, 94));
            featureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            featuresPanel.add(featureLabel);
            featuresPanel.add(Box.createVerticalStrut(5));
        }

        RoundedButton clientLoginBtn = new RoundedButton("Login / Register", 15);
        clientLoginBtn.setPreferredSize(new Dimension(180, 40));
        clientLoginBtn.setMaximumSize(new Dimension(180, 40));
        clientLoginBtn.setBackground(new Color(52, 152, 219));
        clientLoginBtn.setForeground(Color.WHITE);
        clientLoginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        clientLoginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        clientLoginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        clientCard.add(clientIcon);
        clientCard.add(clientLabel);
        clientCard.add(clientDesc);
        clientCard.add(featuresPanel);
        clientCard.add(Box.createVerticalGlue());
        clientCard.add(clientLoginBtn);

        clientLoginBtn.addActionListener(e -> {
            JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
            registerPanel.setBackground(Color.WHITE);

            JLabel registerLabel = new JLabel("Don't have an account? ");
            registerLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
            
            RoundedButton registerBtn = new RoundedButton("Register Now", 20);
            registerBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            registerBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            registerBtn.setBackground(new Color(52, 152, 219));
            registerBtn.setForeground(Color.WHITE);
            registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));

            registerPanel.add(registerLabel);
            registerPanel.add(registerBtn);
            
            JFrame loginFrame = login(registerPanel);
            registerBtn.addActionListener(f -> {
                loginFrame.dispose();
                register();
            });
        });

        // Staff Card

        JPanel staffCard = new JPanel();
        staffCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(30, 20, 30, 20)
        ));
        staffCard.setBackground(Color.white);
        staffCard.setLayout(new BoxLayout(staffCard, BoxLayout.Y_AXIS));
        staffCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel staffIcon = new JLabel("\uf7f3", SwingConstants.CENTER);
        staffIcon.setFont(fontAwesome.deriveFont(Font.PLAIN, 48f));
        staffIcon.setForeground(new Color(231, 76, 60));
        staffIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel staffLabel = new JLabel("STAFF LOGIN");
        staffLabel.setFont(new Font("Cambria", Font.BOLD, 22));
        staffLabel.setForeground(new Color(44, 62, 80));
        staffLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        staffLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        JLabel staffDesc = new JLabel("<html><center>For company employees only to:</center></html>");
        staffDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        staffDesc.setForeground(new Color(127, 140, 141));
        staffDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel staffFeaturesPanel = new JPanel();
        staffFeaturesPanel.setLayout(new BoxLayout(staffFeaturesPanel, BoxLayout.Y_AXIS));
        staffFeaturesPanel.setBackground(Color.WHITE);
        staffFeaturesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        String[] staffFeatures = {"• Manage All Clients", "• Manage applications", "• View Reports"};
        for (String feature : staffFeatures) {
            JLabel featureLabel = new JLabel(feature);
            featureLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            featureLabel.setForeground(new Color(52, 73, 94));
            featureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            staffFeaturesPanel.add(featureLabel);
            staffFeaturesPanel.add(Box.createVerticalStrut(5));
        }

        RoundedButton staffLoginBtn = new RoundedButton("Staff Login", 15);
        staffLoginBtn.setPreferredSize(new Dimension(180, 40));
        staffLoginBtn.setMaximumSize(new Dimension(180, 40));
        staffLoginBtn.setBackground(new Color(231, 76, 60));
        staffLoginBtn.setForeground(Color.WHITE);
        staffLoginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        staffLoginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        staffLoginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        staffLoginBtn.addActionListener(e -> {
           login(null);
        });

        staffCard.add(staffIcon);
        staffCard.add(staffLabel);
        staffCard.add(staffDesc);
        staffCard.add(staffFeaturesPanel);
        staffCard.add(Box.createVerticalGlue());
        staffCard.add(staffLoginBtn);
        
        cardPanel.add(clientCard);
        cardPanel.add(staffCard);

        mainPanel.add(cardPanel);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 15));
        footerPanel.setPreferredSize(new Dimension(900, 60));

        JLabel location = new JLabel("📍 Nairobi, Kenya");
        location.setForeground(new Color(44, 62, 80));
        location.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));

        JLabel telephone = new JLabel("☎ +254 711340632");
        telephone.setForeground(new Color(44, 62, 80));
        telephone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));

        JLabel email = new JLabel("✉ info@uzima.co.ke");
        email.setForeground(new Color(44, 62, 80));
        email.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));

        JLabel copyright = new JLabel("\u00A9 copyright 2025");
        copyright.setForeground(new Color(44, 62, 80));
        copyright.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));

        footerPanel.add(location);
        footerPanel.add(new JLabel("|") {{ setForeground(new Color(44, 62, 80)); }});
        footerPanel.add(telephone);
        footerPanel.add(new JLabel("|") {{ setForeground(new Color(44, 62, 80)); }});
        footerPanel.add(email);
        footerPanel.add(new JLabel("|") {{ setForeground(new Color(44, 62, 80)); }});
        footerPanel.add(copyright);

        
        landingFrame.getContentPane().add(navPanel, BorderLayout.NORTH);
        landingFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        landingFrame.getContentPane().add(footerPanel, BorderLayout.SOUTH);

        landingFrame.setVisible(true);
        landingFrame.setResizable(false);
        landingFrame.setLocationRelativeTo(null);
    }

    public static JPanel navBarPanel(String text, JFrame frame){
        JPanel navPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        navPanel.setPreferredSize(new Dimension(900, 50));
        navPanel.setBackground(new Color(44, 62, 80));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        logoPanel.setBackground(new Color(44, 62, 80));

        ImageIcon logoIcon = new ImageIcon(Dashboard.class.getResource("/Resources/icon32X32.png"));
        JLabel logoLabel = new JLabel(logoIcon);

        JLabel titleLabel = new JLabel(text);
        titleLabel.setFont(new Font("Cambria", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        logoPanel.add(logoLabel);
        logoPanel.add(titleLabel);

        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        navPanel.add(logoPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        navPanel.add(Box.createHorizontalGlue(), gbc);

        RoundedButton backBtn = new RoundedButton("Back", 10);
        backBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.setBackground(new Color(187, 194, 201));
        backBtn.setForeground(Color.WHITE);
        backBtn.addActionListener(e -> {
            frame.dispose();
        });

        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        navPanel.add(backBtn, gbc);

        return navPanel;
    }

    public static JFrame login(JPanel registerPanel){
        JFrame loginFrame = new JFrame();
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(new Dimension(900, 650));
        loginFrame.setTitle("Uzima Bora Borehole System");
        loginFrame.setLayout(new BorderLayout());

        List<Image> icons = new ArrayList<>();
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon16X16.png")).getImage());
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon32X32.png")).getImage());
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon64X64.png")).getImage());

        loginFrame.setIconImages(icons);

        // Nav Panel
        JPanel navPanel = navBarPanel("UZIMA BOREHOLE SERVICES - CLIENT LOGIN", loginFrame);

        // Main Content Area

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        mainPanel.setBackground(Color.WHITE);

        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        messagePanel.setBackground(Color.WHITE);
        messagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel clientIcon = new JLabel("\uf502");
        clientIcon.setFont(fontAwesome.deriveFont(Font.PLAIN, 30f));
        clientIcon.setForeground(new Color(131, 157, 154));
        clientIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel mainLabel = new JLabel("CLIENT LOGIN");
        mainLabel.setFont(new Font("Cambri", Font.BOLD, 25));
        mainLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        messagePanel.add(clientIcon);
        messagePanel.add(mainLabel);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setPreferredSize(new Dimension(600, 400));
        detailsPanel.setMaximumSize(new Dimension(600, 400));
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        detailsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailsPanel.setBackground(new Color(236, 240, 241));

        JLabel welcomeLabel = new JLabel("Welcome back to Uzima Bora Borehole Services", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(44, 62, 80));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

        Font defaultFont = new Font("Calibri", Font.PLAIN, 15);
        UIManager.put("TextField.font", defaultFont); 

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        namePanel.setPreferredSize(new Dimension(250, 30));
        namePanel.setMaximumSize(new Dimension(250, 30));

        JLabel userIcon = new JLabel("\uf2bd", SwingConstants.LEFT);
        userIcon.setFont(fontAwesome.deriveFont(Font.PLAIN, 25f));
        userIcon.setForeground(new Color(131, 157, 154));

        JLabel nameLabel = new JLabel(" Enter Your Name:", SwingConstants.LEFT);
        nameLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        namePanel.add(userIcon);
        namePanel.add(nameLabel);

        JTextField name = new JTextField(30);
        Dimension fixedSize = new Dimension(name.getPreferredSize().width, 30);
        name.setPreferredSize(fixedSize);
        name.setMaximumSize(fixedSize);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        passwordPanel.setMaximumSize(new Dimension(250, 30));
        passwordPanel.setPreferredSize(new Dimension(250, 30));

        JLabel passwordIcon = new JLabel("\uf084", SwingConstants.LEFT);
        passwordIcon.setFont(fontAwesome.deriveFont(Font.PLAIN, 25f));
        passwordIcon.setForeground(new Color(131, 157, 154));

        JLabel passwordLabel = new JLabel(" Enter Your Password:", SwingConstants.LEFT);
        passwordLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        passwordPanel.add(passwordIcon);
        passwordPanel.add(passwordLabel);

        JPasswordField password = new JPasswordField(20);
        password.setPreferredSize(fixedSize);
        password.setMaximumSize(fixedSize);

        RoundedButton loginBtn = new RoundedButton("Log in", 20);
        loginBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setBackground(new Color(41, 74, 102));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.addActionListener(e -> {
            if (InputValidator.validateName(name) && InputValidator.validatePassword(password)) {
                client = clientDAO.getClient(name.getText(), password.getPassword());
                if (client != null) {
                    JOptionPane.showMessageDialog(loginFrame, "User logged in Successfully!!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    landingFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Failed User log in!!", "Failed", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        detailsPanel.add(welcomeLabel);
        detailsPanel.add(Box.createVerticalGlue());
        detailsPanel.add(namePanel);
        detailsPanel.add(Box.createVerticalGlue());
        detailsPanel.add(name);
        detailsPanel.add(Box.createVerticalGlue());
        detailsPanel.add(passwordPanel);
        detailsPanel.add(Box.createVerticalGlue());
        detailsPanel.add(password);
        detailsPanel.add(Box.createVerticalGlue());
        detailsPanel.add(loginBtn);
        

        mainPanel.add(messagePanel);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(detailsPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        
        if (registerPanel != null) {
            mainPanel.add(registerPanel);
        }

        loginFrame.getContentPane().add(navPanel, BorderLayout.NORTH);
        loginFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        loginFrame.setVisible(true);
        loginFrame.setResizable(false); 
        loginFrame.setLocationRelativeTo(null);

        return loginFrame;
    }

    public static void register(){
        JFrame registerFrame = new JFrame();
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerFrame.setSize(new Dimension(900, 650));
        registerFrame.setTitle("Uzima Bora Borehole System");

        List<Image> icons = new ArrayList<>();
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon16X16.png")).getImage());
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon32X32.png")).getImage());
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon64.png")).getImage());

        registerFrame.setIconImages(icons);

        // Navigation Panel
        JPanel navBar = navBarPanel("UZIMA BOREHOLE SERVICES - CLIENT REGISTRATION", registerFrame);

        // Main Content Area       
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        mainPanel.setBackground(new Color(236, 240, 241));
        mainPanel.setPreferredSize(new Dimension(800, 1300));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JLabel mainLabel = new JLabel("📋 Create your client Account", SwingConstants.CENTER);
        mainLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 25));
        mainLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
        // Account Information

        JLabel step1Label = new JLabel("STEP 1 OF 2: ACCOUNT INFORMATION");
        step1Label.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        step1Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel accountPanel = new JPanel();
        accountPanel.setPreferredSize(new Dimension(500, 450));
        accountPanel.setMaximumSize(new Dimension(500, 450));
        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.Y_AXIS));
        accountPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(10, 30, 10, 30)
        ));
        accountPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        accountPanel.setBackground(Color.WHITE);

        Dimension fixedSize = new Dimension(400, 30);

        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        emailPanel.setPreferredSize(new Dimension(250, 40));
        emailPanel.setMaximumSize(new Dimension(250, 40));
        emailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        emailPanel.setBackground(Color.WHITE);

        JLabel emailIcon = new JLabel("\uf27a");
        emailIcon.setFont(fontAwesome.deriveFont(Font.PLAIN, 25f));
        emailIcon.setForeground(new Color(171, 210, 250));

        JLabel emailLabel = new JLabel(" Enter Your Email:");
        emailLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        emailPanel.add(emailIcon);
        emailPanel.add(emailLabel);

        JTextField email = new JTextField(30);
        email.setPreferredSize(fixedSize);
        email.setMaximumSize(fixedSize);
        email.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        passwordPanel.setPreferredSize(new Dimension(250, 40));
        passwordPanel.setMaximumSize(new Dimension(250, 40));
        passwordPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordPanel.setBackground(Color.WHITE);

        JLabel passwordIcon = new JLabel("\uf084");
        passwordIcon.setFont(fontAwesome.deriveFont(Font.PLAIN, 25f));
        passwordIcon.setForeground(new Color(171, 210, 250));

        JLabel passwordLabel = new JLabel(" Enter Your Password:");
        passwordLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        passwordPanel.add(passwordIcon);
        passwordPanel.add(passwordLabel);

        JPasswordField password = new JPasswordField(20);
        password.setPreferredSize(fixedSize);
        password.setMaximumSize(fixedSize);
        password.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel strengthLabel = new JLabel();
        strengthLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));
        strengthLabel.setBackground(Color.WHITE);

        JPanel confirmPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        confirmPanel.setMaximumSize(new Dimension(300, 40));
        confirmPanel.setPreferredSize(new Dimension(300, 40));
        confirmPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        confirmPanel.setBackground(Color.WHITE);

        JLabel confirmIcon = new JLabel("\uf09c");
        confirmIcon.setFont(fontAwesome.deriveFont(Font.PLAIN, 25f));
        confirmIcon.setForeground(new Color(171, 210, 250));

        JLabel confirmLabel = new JLabel(" Confirm Your Password:");
        confirmLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        confirmPanel.add(confirmIcon);
        confirmPanel.add(confirmLabel);

        JPasswordField confirm = new JPasswordField(20);
        confirm.setPreferredSize(fixedSize);
        confirm.setMaximumSize(fixedSize);
        confirm.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel matchLabel = new JLabel("");
        matchLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 15));

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

        JPanel telephonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        telephonePanel.setMaximumSize(new Dimension(300, 40));
        telephonePanel.setPreferredSize(new Dimension(300, 40));
        telephonePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        telephonePanel.setBackground(Color.WHITE);

        JLabel telephoneIcon = new JLabel("\uf2a0");
        telephoneIcon.setFont(fontAwesome);
        telephoneIcon.setForeground(new Color(171, 210, 250));

        JLabel telephoneLabel = new JLabel(" Enter Phone Number:");
        telephoneLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        telephonePanel.add(telephoneIcon);
        telephonePanel.add(telephoneLabel);

        JTextField telephone = new JTextField(30);
        telephone.setPreferredSize(fixedSize);
        telephone.setMaximumSize(fixedSize);
        telephone.setAlignmentX(Component.LEFT_ALIGNMENT);

        accountPanel.add(emailPanel);
        accountPanel.add(Box.createVerticalStrut(5));
        accountPanel.add(email);
        accountPanel.add(Box.createVerticalGlue());

        accountPanel.add(passwordPanel);
        accountPanel.add(Box.createVerticalStrut(5));
        accountPanel.add(password);
        accountPanel.add(Box.createVerticalStrut(5));
        accountPanel.add(strengthLabel);
        accountPanel.add(Box.createVerticalGlue());

        accountPanel.add(confirmPanel);
        accountPanel.add(Box.createVerticalStrut(5));
        accountPanel.add(confirm);
        accountPanel.add(Box.createVerticalStrut(5));
        accountPanel.add(matchLabel);
        accountPanel.add(Box.createVerticalGlue());

        accountPanel.add(telephonePanel);
        accountPanel.add(Box.createVerticalStrut(5));
        accountPanel.add(telephone);
        accountPanel.add(Box.createVerticalGlue());

        // Personal Information

        JLabel step2Label = new JLabel("STEP 2 OF 2: PERSONAL INFORMATION");
        step2Label.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
        step2Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel personalPanel = new JPanel();
        personalPanel.setPreferredSize(new Dimension(500, 480));
        personalPanel.setMaximumSize(new Dimension(500, 480));
        personalPanel.setLayout(new BoxLayout(personalPanel, BoxLayout.Y_AXIS));
        personalPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(10, 30, 10, 30)
        ));
        personalPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        personalPanel.setBackground(Color.WHITE);

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        namePanel.setPreferredSize(new Dimension(250, 40));
        namePanel.setMinimumSize(new Dimension(250, 40));
        namePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        namePanel.setBackground(Color.WHITE);

        JLabel userIcon = new JLabel("\uf2bd");
        userIcon.setFont(fontAwesome.deriveFont(Font.PLAIN, 25f));
        userIcon.setForeground(new Color(171, 210, 250));

        JLabel nameLabel = new JLabel(" Full Name / Company Name : *");
        nameLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        namePanel.add(userIcon);
        namePanel.add(nameLabel);

        JTextField name = new JTextField(30);
        name.setPreferredSize(fixedSize);
        name.setMaximumSize(fixedSize);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        addressPanel.setMaximumSize(new Dimension(250, 40));
        addressPanel.setPreferredSize(new Dimension(250, 40));
        addressPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        addressPanel.setBackground(Color.WHITE);

        JLabel addressIcon = new JLabel("\uf3c5");
        addressIcon.setFont(fontAwesome.deriveFont(Font.PLAIN, 25f));
        addressIcon.setForeground(new Color(171, 210, 250));

        JLabel addressLabel = new JLabel(" Enter your Address: *");
        addressLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        addressPanel.add(addressIcon);
        addressPanel.add(addressLabel);

        JTextArea address = new JTextArea();
        address.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true));
        address.setPreferredSize(new Dimension(400, 80));
        address.setMaximumSize(new Dimension(400, 80));
        address.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel locationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        locationPanel.setMaximumSize(new Dimension(300, 40));
        locationPanel.setPreferredSize(new Dimension(300, 40));
        locationPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        locationPanel.setBackground(Color.WHITE);

        JLabel locationIcon = new JLabel("\uf08d");
        locationIcon.setFont(fontAwesome.deriveFont(Font.PLAIN, 25f));
        locationIcon.setForeground(new Color(171, 210, 250));

        JLabel locationLabel = new JLabel(" Enter Borehole location: *");
        locationLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        locationPanel.add(locationIcon);
        locationPanel.add(locationLabel);

        JTextArea location = new JTextArea();
        location.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true));
        location.setPreferredSize(new Dimension(400, 80));
        location.setMaximumSize(new Dimension(400, 80));
        location.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        categoryPanel.setMaximumSize(new Dimension(300, 40));
        categoryPanel.setPreferredSize(new Dimension(300, 40));
        categoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryPanel.setBackground(Color.WHITE);

        JLabel categoryIcon = new JLabel("\uf007");
        categoryIcon.setFont(fontAwesome.deriveFont(Font.PLAIN, 25f));
        categoryIcon.setForeground(new Color(171, 210, 250));

        JLabel categoryLabel = new JLabel(" Select Client Category: *");
        categoryLabel.setFont(new Font("Calibri", Font.PLAIN, 20));

        ClientCategory[] options = {ClientCategory.COMMERCIAL, ClientCategory.DOMESTIC, ClientCategory.INDUSTRIAL};
        JComboBox<ClientCategory> category = new JComboBox<>(options);
        category.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        category.setMaximumSize(fixedSize);
        category.setFont(new Font("Cambria", Font.PLAIN, 16));
        category.setAlignmentX(Component.LEFT_ALIGNMENT);
        category.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        categoryPanel.add(categoryIcon);
        categoryPanel.add(categoryLabel);

        personalPanel.add(namePanel);
        personalPanel.add(Box.createVerticalStrut(5));
        personalPanel.add(name);
        personalPanel.add(Box.createVerticalGlue());

        personalPanel.add(addressPanel);
        personalPanel.add(Box.createVerticalStrut(5));
        personalPanel.add(address);
        personalPanel.add(Box.createVerticalGlue());

        personalPanel.add(locationPanel);
        personalPanel.add(Box.createVerticalStrut(5));
        personalPanel.add(location);
        personalPanel.add(Box.createVerticalGlue());

        personalPanel.add(categoryPanel);
        personalPanel.add(Box.createVerticalStrut(5));
        personalPanel.add(category);
        personalPanel.add(Box.createVerticalGlue());
        
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
                landingFrame.dispose();
                registerFrame.dispose();
            }
        });

        mainPanel.add(mainLabel);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(step1Label);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(accountPanel);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(step2Label);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(personalPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(registerBtn);
        mainPanel.add(Box.createVerticalStrut(10));

        registerFrame.getContentPane().add(navBar, BorderLayout.NORTH);
        registerFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        registerFrame.setVisible(true);
        registerFrame.setLocationRelativeTo(null);
    }

    public static void clientDashboard(Client client){
        JFrame dashFrame = new JFrame("Uzima Bora Client Portal");
        dashFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashFrame.setSize(new Dimension(900, 650));
        dashFrame.setLayout(new BorderLayout());

        List<Image> icons = new ArrayList<>();
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon16X16.png")).getImage());
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon32X32.png")).getImage());
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon64X64.png")).getImage());

        dashFrame.setIconImages(icons);

        // Nav Panel

        JPanel navPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        navPanel.setBackground(new Color(44, 62, 80));
        navPanel.setPreferredSize(new Dimension(900, 80));
        navPanel.setMaximumSize(new Dimension(900, 50));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        logoPanel.setBackground(new Color(44, 62, 80));

        ImageIcon icon = new ImageIcon(Dashboard.class.getResource("/Resources/icon32X32.png"));
        JLabel logo = new JLabel(icon);

        JLabel titleLabel = new JLabel("UZIMA BORA CLIENT PORTAL");
        titleLabel.setFont(new Font("Cambria", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        logoPanel.add(logo);
        logoPanel.add(titleLabel);

        gbc.gridx = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        navPanel.add(logoPanel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        navPanel.add(Box.createHorizontalGlue(), gbc);

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        userPanel.setBackground(new Color(44, 62, 80));

        JLabel userIcon = new JLabel("\ue1b0");
        userIcon.setFont(fontAwesome.deriveFont(Font.PLAIN, 25f));
        userIcon.setForeground(Color.WHITE);

        JLabel welcomeLabel = new JLabel("Welcome, " + client.getUsername());
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        userPanel.add(userIcon);
        userPanel.add(welcomeLabel);

        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        navPanel.add(userPanel, gbc);

        // Main Content Area

        dashFrame.getContentPane().add(navPanel, BorderLayout.NORTH);
        dashFrame.setVisible(true);
        dashFrame.setLocationRelativeTo(null);
    }
}