package Ui;
import java.awt.*;
import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.*;

import database.*;
import model.*;
import util.FeeCalculator;

public class ServiceRegistration {
    private Client client;
    private static String drillingSelection = "0.0";
    private static String pumpSelection = "0.0";
    private static Application app;

    public ServiceRegistration(Client client){
        this.client = client;
        app = new Application(client);
    }

    public void applicationForm(){
        JFrame formFrame = new JFrame("UZIMA BORA BOREHOLE SYSTEM");
        formFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        formFrame.setSize(new Dimension(1000, 850));
        formFrame.setMinimumSize(new Dimension(1000, 800));
        formFrame.setLayout(new BorderLayout());
        formFrame.setBackground(new Color(236, 240, 241));

        JPanel navBar = Dashboard.navBarPanel("APPLY FOR SERVICE", formFrame);
        navBar.setPreferredSize(new Dimension(1000, 80));

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(236, 240, 241));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setPreferredSize(new Dimension(900, 1100));
        formPanel.setMaximumSize(new Dimension(900, 1100));
        formPanel.setBackground(new Color(250,250, 250));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(30, 20, 30, 20)
        ));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("SERVICES REQUIRED (Select all that apply)");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setPreferredSize(new Dimension(1000, 40));
        formPanel.add(title);
        formPanel.add(Box.createVerticalStrut(25));

        // Borehole Drilling

        JPanel boreholePanel = new JPanel();
        boreholePanel.setLayout(new BoxLayout(boreholePanel, BoxLayout.Y_AXIS));
        boreholePanel.setBackground(Color.WHITE);
        boreholePanel.setAlignmentX(Component.LEFT_ALIGNMENT);  

        JCheckBox boreholeCheckBox = new JCheckBox("BOREHOLE DRILLING");
        boreholeCheckBox.setPreferredSize(new Dimension(18, 18));
        boreholeCheckBox.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boreholeCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));    
        boreholeCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        boreholeCheckBox.setBackground(Color.WHITE);
        boreholePanel.add(boreholeCheckBox);
        boreholePanel.add(Box.createVerticalStrut(5));

        JPanel boreholeSelectionPanel = new JPanel();
        boreholeSelectionPanel.setBackground(new Color(212,239,223));
        boreholeSelectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        boreholeSelectionPanel.setLayout(new BoxLayout(boreholeSelectionPanel, BoxLayout.Y_AXIS));
        boreholeSelectionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JPanel dRadioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        dRadioPanel.setOpaque(false);
        dRadioPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel drillingLabel = new JLabel("Drilling Type: ");
        drillingLabel.setFont(new Font("Cambria", Font.PLAIN, 16));
        dRadioPanel.add(drillingLabel);

        ButtonGroup drillingGroup = new ButtonGroup();
        DrilingType[] radioTexts = {DrilingType.CORE, DrilingType.SYMMETRIC, DrilingType.GEO_TECHNICAL};
        JRadioButton[] radioButtons = new JRadioButton[radioTexts.length];

        for (int i=0; i<radioTexts.length; i++) {
            Double downPayment = radioTexts[i].getDownPayment();
            radioButtons[i] = new JRadioButton(radioTexts[i].name());
            radioButtons[i].setActionCommand(Double.toString(downPayment));
            radioButtons[i].setOpaque(false);
            radioButtons[i].setFont(new Font("Calibri", Font.PLAIN, 16));
            radioButtons[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            drillingGroup.add(radioButtons[i]);
            dRadioPanel.add(radioButtons[i]);
        }
        boreholeSelectionPanel.add(dRadioPanel);
        
        JLabel downPaymentLabel = new JLabel("Down Payment: " + drillingSelection + " (Auto-calculated)");
        downPaymentLabel.setFont(new Font("Cambria", Font.BOLD, 16));
        downPaymentLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        for (JRadioButton radioBtn : radioButtons) {
            radioBtn.addActionListener(e -> {
                String selectedPayment = drillingGroup.getSelection().getActionCommand();
                drillingSelection = radioBtn.getText();
                downPaymentLabel.setText("Down Payment: KES " + selectedPayment + " (Auto-calculated)");
            });
        }

        JPanel depthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        depthPanel.setOpaque(false);
        depthPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel depthLabel = new JLabel("Borehole Depth: ");
        depthLabel.setFont(new Font("Cambria", Font.PLAIN, 16));

        JTextField depth = new JTextField(10);
        depth.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189,195,199), 1, true),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        depth.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel meters = new JLabel(" meters");
        meters.setFont(new Font("Segoe UI", Font.BOLD, 14));
        meters.setOpaque(false);

        depthPanel.add(depthLabel);
        depthPanel.add(depth);
        depthPanel.add(meters);
        boreholeSelectionPanel.add(depthPanel);
        boreholeSelectionPanel.add(downPaymentLabel);

        boreholePanel.add(boreholeSelectionPanel);
        formPanel.add(boreholePanel);
        formPanel.add(Box.createVerticalStrut(25));
        
        // Water Pump Installation

        JPanel pumpPanel = new JPanel();
        pumpPanel.setLayout(new BoxLayout(pumpPanel, BoxLayout.Y_AXIS));
        pumpPanel.setBackground(Color.WHITE);
        pumpPanel.setAlignmentX(Component.LEFT_ALIGNMENT);   

        JCheckBox pumpCheckBox = new JCheckBox("WATER PUMP INSTALLATION");
        pumpCheckBox.setPreferredSize(new Dimension(18, 18));
        pumpCheckBox.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pumpCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));    
        pumpCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        pumpCheckBox.setBackground(Color.WHITE);
        pumpPanel.add(pumpCheckBox);
        pumpPanel.add(Box.createVerticalStrut(5));

        JPanel pumpSelectionPanel = new JPanel();
       pumpSelectionPanel.setBackground(new Color(212,239,223));
       pumpSelectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
       pumpSelectionPanel.setLayout(new BoxLayout(pumpSelectionPanel, BoxLayout.Y_AXIS));
       pumpSelectionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        JPanel pumpRadioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pumpRadioPanel.setOpaque(false);
        pumpRadioPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel pumpLabel = new JLabel("Pump Type: ");
        pumpLabel.setFont(new Font("Cambria", Font.PLAIN, 16));
        pumpRadioPanel.add(pumpLabel);

        ButtonGroup pumpGroup = new ButtonGroup();
        PumpType[] pumps = {PumpType.HAND, PumpType.SOLAR, PumpType.SUBMERSIBLE_ELECTRIC};
        JRadioButton[] pumpButtons = new JRadioButton[pumps.length];
        
        for (int i=0; i<pumps.length; i++) {
            Double baseCost = pumps[i].getBaseCost();
            pumpButtons[i] = new JRadioButton(pumps[i].name());
            pumpButtons[i].setActionCommand(Double.toString(baseCost));
            pumpButtons[i].setOpaque(false);
            pumpButtons[i].setFont(new Font("Calibri", Font.PLAIN, 16));
            pumpButtons[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            pumpGroup.add(pumpButtons[i]);
            pumpRadioPanel.add(pumpButtons[i]);
        }
        pumpSelectionPanel.add(pumpRadioPanel);

        JLabel baseCostLabel = new JLabel("Base Cost: " + pumpSelection + " (Auto-calculated)");
        baseCostLabel.setFont(new Font("Cambria", Font.BOLD, 16));
        baseCostLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (JRadioButton radioBtn : pumpButtons) {
            radioBtn.addActionListener(e -> {
                String selectedPayment = pumpGroup.getSelection().getActionCommand();
                pumpSelection = radioBtn.getText();
                baseCostLabel.setText("Base Cost: " + selectedPayment + " (Auto-calculated)");
            });
        }

        JPanel tankDepthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        tankDepthPanel.setOpaque(false);
        tankDepthPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tankDepthLabel = new JLabel("Borehole Depth: ");
        tankDepthLabel.setFont(new Font("Cambria", Font.PLAIN, 16));

        JTextField depth2 = new JTextField(10);
        depth2.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189,195,199), 1, true),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        depth2.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel meters2 = new JLabel(" meters");
        meters2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        meters2.setOpaque(false);

        tankDepthPanel.add(tankDepthLabel);
        tankDepthPanel.add(depth2);
        tankDepthPanel.add(meters2);

        JPanel heightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        heightPanel.setOpaque(false);
        heightPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heightLabel = new JLabel("Tank Height Above Ground: ");
        heightLabel.setFont(new Font("Cambria", Font.PLAIN, 16));

        JTextField height = new JTextField(10);
        height.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189,195,199), 1, true),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        height.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel meters3 = new JLabel(" meters");
        meters3.setFont(new Font("Segoe UI", Font.BOLD, 14));
        meters3.setOpaque(false);

        heightPanel.add(heightLabel);
        heightPanel.add(height);
        heightPanel.add(meters3);
        pumpSelectionPanel.add(tankDepthPanel);
        pumpSelectionPanel.add(heightPanel);
        pumpSelectionPanel.add(Box.createVerticalStrut(15));

        pumpSelectionPanel.add(baseCostLabel);
        pumpPanel.add(pumpSelectionPanel);
        formPanel.add(pumpPanel);
        formPanel.add(Box.createVerticalStrut(25));

        // Tank Installation

        JPanel tankPanel = new JPanel();
        tankPanel.setLayout(new BoxLayout(tankPanel, BoxLayout.Y_AXIS));
        tankPanel.setBackground(Color.WHITE);
        tankPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JCheckBox tankCheckBox = new JCheckBox("WATER TANK INSTALLATION");
        tankCheckBox.setPreferredSize(new Dimension(18, 18));
        tankCheckBox.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tankCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));    
        tankCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        tankCheckBox.setBackground(Color.WHITE);
        tankPanel.add(tankCheckBox);
        tankPanel.add(Box.createVerticalStrut(5));

        JPanel tankDetails = new JPanel();
        tankDetails.setBackground(new Color(212,239,223));
        tankDetails.setAlignmentX(Component.LEFT_ALIGNMENT);
        tankDetails.setLayout(new BoxLayout(tankDetails, BoxLayout.Y_AXIS));
        tankDetails.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        JPanel capacityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        capacityPanel.setOpaque(false);
        capacityPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField capacity = new JTextField(10);
        capacity.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189,195,199), 1, true),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        capacity.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel capacityLabel = new JLabel("Tank Capacity: ");
        capacityLabel.setFont(new Font("Cambria", Font.PLAIN, 16));

        JLabel litresLabel = new JLabel(" litres");
        litresLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        litresLabel.setOpaque(false);

        JLabel cost = new JLabel("Cost per Litre: KES 50.00");
        cost.setFont(new Font("Cambria", Font.BOLD, 16));

        capacityPanel.add(capacityLabel);
        capacityPanel.add(capacity);
        capacityPanel.add(litresLabel);

        tankDetails.add(capacityPanel);
        tankDetails.add(cost);

        tankPanel.add(tankDetails);
        formPanel.add(tankPanel);
        formPanel.add(Box.createVerticalStrut(25));
        
        // Plumbing Services

        JPanel plumbingPanel = new JPanel();
        plumbingPanel.setLayout(new BoxLayout(plumbingPanel, BoxLayout.Y_AXIS));
        plumbingPanel.setBackground(Color.WHITE);
        plumbingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JCheckBox plumbingCheckBox = new JCheckBox("PLUMBING SERVICES");
        plumbingCheckBox.setPreferredSize(new Dimension(18, 18));
        plumbingCheckBox.setFont(new Font("Segoe UI", Font.BOLD, 14));
        plumbingCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));    
        plumbingCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        plumbingCheckBox.setBackground(Color.WHITE);
        plumbingPanel.add(plumbingCheckBox);
        plumbingPanel.add(Box.createVerticalStrut(5));

        JPanel plumbingDetails = new JPanel();
        plumbingDetails.setBackground(new Color(212,239,223));
        plumbingDetails.setAlignmentX(Component.LEFT_ALIGNMENT);
        plumbingDetails.setLayout(new BoxLayout(plumbingDetails, BoxLayout.Y_AXIS));
        plumbingDetails.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        typePanel.setOpaque(false);
        typePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel pipetypeLabel = new JLabel("Pipe Type: ");
        pipetypeLabel.setFont(new Font("Cambria", Font.PLAIN, 16));
        PipeType[] pipeOptions = {PipeType.PVC, PipeType.HDPE, PipeType.STEEL, PipeType.GALVANIZED_STEEL, PipeType.STAINLESS_STEEL};

        JComboBox<PipeType> type = new JComboBox<>(pipeOptions);
        typePanel.add(pipetypeLabel);
        typePanel.add(type);
        plumbingDetails.add(typePanel);

        JPanel diameterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        diameterPanel.setOpaque(false);
        diameterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField diameter = new JTextField(10);
        diameter.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189,195,199), 1, true),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        diameter.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel diameterLabel = new JLabel("Diameter: ");
        diameterLabel.setFont(new Font("Cambria", Font.PLAIN, 16));

        JLabel inches = new JLabel(" inches");
        inches.setFont(new Font("Segoe UI", Font.BOLD, 14));
        inches.setOpaque(false);

        diameterPanel.add(diameterLabel);
        diameterPanel.add(diameter);
        diameterPanel.add(inches);
        plumbingDetails.add(diameterPanel);

        JPanel lengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        lengthPanel.setOpaque(false);
        lengthPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField length = new JTextField(10);
        length.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189,195,199), 1, true),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        length.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel lengthLabel = new JLabel("Pipe Length: ");
        lengthLabel.setFont(new Font("Cambria", Font.PLAIN, 16));

        JLabel meters4 = new JLabel(" meters");
        meters4.setFont(new Font("Segoe UI", Font.BOLD, 14));
        meters4.setOpaque(false);

        lengthPanel.add(lengthLabel);
        lengthPanel.add(length);
        lengthPanel.add(meters4);
        plumbingDetails.add(lengthPanel);

        JPanel outletsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        outletsPanel.setOpaque(false);
        outletsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField outlets = new JTextField(8);
        outlets.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189,195,199), 1, true),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        outlets.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel outletsLabel = new JLabel("Number of Outlets: ");
        outletsLabel.setFont(new Font("Cambria", Font.PLAIN, 16));

        outletsPanel.add(outletsLabel);
        outletsPanel.add(outlets);
        plumbingDetails.add(outletsPanel);

        plumbingPanel.add(plumbingDetails);
        plumbingPanel.add(Box.createVerticalStrut(25));
        formPanel.add(plumbingPanel);      
        
        JLabel notesLabel = new JLabel("Additional Notes:");
        notesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        notesLabel.setFont(new Font("Cambria", Font.BOLD, 18));

        JTextArea additionalNotes = new JTextArea(15, 35);
        additionalNotes.setBorder(BorderFactory.createLineBorder(new Color(189,195,199), 2, true));
        additionalNotes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        additionalNotes.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(notesLabel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(additionalNotes);

        JPanel btnsPanel = new JPanel(new GridLayout(1,2, 30, 10));
        btnsPanel.setPreferredSize(new Dimension(500, 50));
        btnsPanel.setMaximumSize(new Dimension(500, 50));
        btnsPanel.setOpaque(false);

        RoundedButton calculateBtn = new RoundedButton("Calculate Quote", 15);
        calculateBtn.setBackground(new Color(149,165,166));
        calculateBtn.setForeground(Color.WHITE);
        calculateBtn.setPreferredSize(new Dimension(150, 40));
        calculateBtn.setMaximumSize(new Dimension(150, 40));
        calculateBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        calculateBtn.setFont(new Font("Calibri", Font.BOLD, 14));
        calculateBtn.addActionListener(e -> {
            appliedDrilling(boreholeCheckBox, client, boreholeSelectionPanel, depth);
            appliedpPumpInstallation(pumpCheckBox, client, tankDetails, depth2, height);
            appliedTankInstallation(tankCheckBox, client, capacity);
            appliedPlumbing(plumbingCheckBox, client, plumbingDetails, diameter, type, length, outlets);
            quoteGenerator(client, app);
        });
        btnsPanel.add(calculateBtn);

        RoundedButton submit = new RoundedButton("Submit Application", 15);
        submit.setPreferredSize(new Dimension(150, 40));
        submit.setMaximumSize(new Dimension(150, 40));
        submit.setBackground(new Color(52,152,219));
        submit.setForeground(Color.WHITE);
        submit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submit.setFont(new Font("Calibri", Font.BOLD, 14));
        btnsPanel.add(submit);

        submit.addActionListener(e -> {
            DrillingService drillingService = appliedDrilling(boreholeCheckBox, client, boreholeSelectionPanel, depth);
            if (drillingService != null) {
                DrillingDAO.insertDrillingService(drillingService);
            }

            PumpInstallation pumpInstallation = appliedpPumpInstallation(pumpCheckBox, client, tankDetails, depth2, height);
            if (pumpInstallation != null) {
              PumpDAO.insertPumpService(pumpInstallation);  
            }

            TankInstallation tankInstallation = appliedTankInstallation(tankCheckBox, client, capacity);
            if (tankInstallation != null) {
                TankDAO.insertTankService(tankInstallation);
            }

            PlumbingService plumbingService = appliedPlumbing(plumbingCheckBox, client, plumbingDetails, diameter, type, length, outlets);
            if (plumbingService != null) {
                PlumbingDAO.insertPlumbingService(plumbingService);
            }

            app.setEstimatedCost();
            app.setAdditionalNotes(additionalNotes.getText());
            app.setSubmittedDate(Timestamp.valueOf(LocalDateTime.now()));
            boolean status = ApplicationDAO.insertApplication(app);
            if (status) {
                confirmationFrame(app);
            }
        });

        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(btnsPanel);

        JPanel footer = Dashboard.footerPanel();
        footer.setBackground(new Color(44, 62, 80));
        for (Component c : footer.getComponents()) {
            c.setForeground(Color.WHITE);
        }

        formFrame.getContentPane().add(navBar, BorderLayout.NORTH);
        formFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        formFrame.getContentPane().add(footer, BorderLayout.SOUTH);
        formFrame.setVisible(true);
        formFrame.setLocationRelativeTo(null);
    }

    private static DrillingService appliedDrilling(JCheckBox checkBox, Client client, JPanel detailsPanel, JTextField depth){
        DrillingService drillingService = null;
        if (checkBox.isSelected() && drillingSelection != "0.0" && !checkEmptyTextField(detailsPanel)) {
                drillingService = new DrillingService(client.getClientId(), DrilingType.valueOf(drillingSelection), Double.parseDouble(depth.getText()), LocalDate.now().plusDays(14));
                app.setNeedsDrilling(true);
                app.setDrillingType(drillingService.getDrilingType());
                app.setBoreholeLocation(client.getBoreholeLocation());
                app.setEstimatedDepth(drillingService.getBoreholeDepth());
        }
        return drillingService;
    }

    private static PumpInstallation appliedpPumpInstallation(JCheckBox checkBox, Client client, JPanel detailsPanel, JTextField depth, JTextField height){
        PumpInstallation pumpInstallation = null;
        if (checkBox.isSelected() && pumpSelection != "0.0" && !checkEmptyTextField(detailsPanel)) {
            pumpInstallation = new PumpInstallation(client.getClientId(), PumpType.valueOf(pumpSelection), Double.parseDouble(depth.getText()), Double.parseDouble(height.getText()), LocalDate.now().plusDays(14));
            app.setNeedsPump(true);
            app.setPumpType(pumpInstallation.getPumpType());
            app.setTankHeight(pumpInstallation.getTankHeight());
        }
        return pumpInstallation;
    }

    private static TankInstallation appliedTankInstallation(JCheckBox checkBox, Client client, JTextField capacity){
        TankInstallation tankInstallation = null;
        if (checkBox.isSelected() && capacity.getText() != null) {
                tankInstallation = new TankInstallation(client.getClientId(), Double.parseDouble(capacity.getText()), LocalDate.now().plusDays(14));
                app.setNeedsTank(true);
                app.setTankCapacity(tankInstallation.getCapacity());
        }
        return tankInstallation;
    }
    private static PlumbingService appliedPlumbing(JCheckBox checkBox, Client client, JPanel detailsPanel, JTextField diameter, JComboBox<PipeType> type, JTextField length, JTextField outlets){
        PlumbingService plumbingService = null;
        if (checkBox.isSelected() && !checkEmptyTextField(detailsPanel)) {
            plumbingService = new PlumbingService(client.getClientId(), (PipeType) type.getSelectedItem(), Double.parseDouble(diameter.getText()), Double.parseDouble(length.getText()), Integer.parseInt(outlets.getText()), LocalDate.now().plusDays(14));
            app.setNeedsPlumbing(true);
            app.setPipeType(plumbingService.getPipeType());
            app.setPipeDiameter(plumbingService.getPipeDiameter());
            app.setPipeLength(plumbingService.getPipeLength());
            app.setNumberOfOutlets(plumbingService.getNumOfOutlets());
        }
        return plumbingService;
    }

    private static boolean checkEmptyTextField(JPanel panel){
        boolean isEmpty = false;
        for (Component component : panel.getComponents()) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                if (textField.getText() == null) {
                    isEmpty = true;
                    JOptionPane.showMessageDialog(null, "NO empty fields allowed in selected section!!" + "Warning!!" + JOptionPane.WARNING_MESSAGE);
                    break;
                }
            }
        }
        return isEmpty;
    }

    private static void quoteGenerator(Client client, Application app){
        ArrayList<Double> charges = new ArrayList<>();

        JFrame quoteFrame = new JFrame("UZIMA BORA BOREHOLE SYSTEM");
        quoteFrame.setSize(new Dimension(650, 650));
        quoteFrame.setLayout(new BorderLayout());
        quoteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<Image> icons = new ArrayList<>();
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon16X16.png")).getImage());
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon32X32.png")).getImage());
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon64X64.png")).getImage());

        quoteFrame.setIconImages(icons);

        JPanel navBar = Dashboard.navBarPanel("ESTIMATED COST BREAKDOWN", quoteFrame);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(236, 240, 241));
        
        JScrollPane scrollBar = new JScrollPane(mainPanel);
        scrollBar.setBorder(null);
        scrollBar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollBar.getVerticalScrollBar().setUnitIncrement(16);

        JLabel title = new JLabel("CLIENT: " + client.getUsername() + "(" + client.getClientCategory() + ")");
        title.setFont(new Font("Cambria", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(44, 62, 80));

        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(15));

        JLabel subTitle = new JLabel("COST BREAKDOWN");
        subTitle.setFont(new Font("Cambria", Font.BOLD, 18));
        subTitle.setForeground(new Color(127, 140, 141));
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(subTitle);
        mainPanel.add(Box.createVerticalStrut(25));

        JPanel costPanel = new JPanel();
        costPanel.setBackground(Color.WHITE);
        costPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        costPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(30, 20, 30, 20)
        ));
        costPanel.setLayout(new BoxLayout(costPanel, BoxLayout.Y_AXIS));

        JPanel surveyPanel = new JPanel(new GridBagLayout());
        surveyPanel.setPreferredSize(new Dimension(500, 30));
        surveyPanel.setMaximumSize(new Dimension(500, 30));
        surveyPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel surveyLabel = new JLabel("Survey Fees:");
        surveyLabel.setFont(new Font("Calibri", Font.PLAIN, 16));

        gbc.anchor =  GridBagConstraints.EAST;
        gbc.weightx = 0;
        gbc.gridx = 0;
        surveyPanel.add(surveyLabel, gbc);

        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        surveyPanel.add(Box.createHorizontalGlue(), gbc);

        JLabel surveyAmount = new JLabel("KES " + Double.toString(client.getClientCategory().getSurveyFees()));
        surveyAmount.setFont(new Font("Calibri", Font.PLAIN, 16));
        charges.add(client.getClientCategory().getSurveyFees());

        gbc.weightx = 0;
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        surveyPanel.add(surveyAmount, gbc);
        costPanel.add(surveyPanel);

        JPanel localPanel = new JPanel(new GridBagLayout());
        localPanel.setPreferredSize(new Dimension(500, 30));
        localPanel.setMaximumSize(new Dimension(500, 30));
        localPanel.setOpaque(false);
        
        JLabel localLabel = new JLabel("Local Authority Fees:");
        localLabel.setFont(new Font("Calibri", Font.PLAIN, 16));

        gbc.anchor =  GridBagConstraints.EAST;
        gbc.weightx = 0;
        gbc.gridx = 0;
        localPanel.add(localLabel, gbc);

        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        localPanel.add(Box.createHorizontalGlue(), gbc);

        JLabel localAmount = new JLabel("KES " + Double.toString(client.getClientCategory().getLocalAuthorityFees()));
        localAmount.setFont(new Font("Calibri", Font.PLAIN, 16));
        charges.add(client.getClientCategory().getLocalAuthorityFees());

        gbc.weightx = 0;
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        localPanel.add(localAmount, gbc);
        costPanel.add(localPanel);
        costPanel.add(Box.createVerticalStrut(25));

        // Drilling

        if (app.isNeedsDrilling()) {
            JPanel drillingLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            drillingLabelPanel.setOpaque(false);
            drillingLabelPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

            JLabel drillingLabel = new JLabel(app.getDrillingType().name() + " Drilling (" + Double.toString(app.getEstimatedDepth()) + "m):", SwingConstants.LEFT);
            drillingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            drillingLabelPanel.add(drillingLabel);
            costPanel.add(drillingLabelPanel);

            JPanel paymentPanel = new JPanel(new GridBagLayout());
            paymentPanel.setPreferredSize(new Dimension(500, 30));
            paymentPanel.setMaximumSize(new Dimension(500, 30));
            paymentPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            paymentPanel.setOpaque(false);

            JLabel downPaymentLabel = new JLabel("- Down Payment:");
            downPaymentLabel.setFont(new Font("Calibri", Font.PLAIN, 16));

            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0;
            gbc.gridx = 0;
            paymentPanel.add(downPaymentLabel, gbc);

            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            paymentPanel.add(Box.createHorizontalGlue(), gbc);

            JLabel downPayment = new JLabel("KES " + Double.toString(app.getDrillingType().getDownPayment()));
            downPayment.setFont(new Font("Calibri", Font.PLAIN, 16));
            
            gbc.weightx = 0;
            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.WEST;
            paymentPanel.add(downPayment, gbc);
            costPanel.add(paymentPanel);
           
            JPanel depthPanel = new JPanel(new GridBagLayout());
            depthPanel.setPreferredSize(new Dimension(500, 30));
            depthPanel.setMaximumSize(new Dimension(500, 30));
            depthPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            depthPanel.setOpaque(false);

            JLabel depthChargesLabel = new JLabel("- Depth Charges (" + Double.toString(app.getEstimatedDepth()) + "m):");
            depthChargesLabel.setFont(new Font("Calibri", Font.PLAIN, 16));

            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0;
            gbc.gridx = 0;
            depthPanel.add(depthChargesLabel, gbc);

            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            depthPanel.add(Box.createHorizontalGlue(), gbc);

            JLabel depthCharges = new JLabel("KES " + Double.toString(FeeCalculator.calculateDepthCharges(app.getEstimatedDepth())));
            depthCharges.setFont(new Font("Calibri", Font.PLAIN, 16));
            
            gbc.weightx = 0;
            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.WEST;
            depthPanel.add(depthCharges, gbc);
            costPanel.add(depthPanel);

            JPanel subTotalPanel = new JPanel(new GridBagLayout());
            subTotalPanel.setPreferredSize(new Dimension(500, 30));
            subTotalPanel.setMaximumSize(new Dimension(500, 30));
            subTotalPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            subTotalPanel.setOpaque(false);

            JLabel subTotalLabel = new JLabel("Subtotal Drilling:");
            subTotalLabel.setFont(new Font("Calibri", Font.PLAIN, 16));

            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0;
            gbc.gridx = 0;
            subTotalPanel.add(subTotalLabel, gbc);

            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            subTotalPanel.add(Box.createHorizontalGlue(), gbc);

            JLabel subTotal = new JLabel("KES " + Double.toString(app.getDrillingType().getDownPayment() + FeeCalculator.calculateDepthCharges(app.getEstimatedDepth())));
            subTotal.setFont(new Font("Calibri", Font.PLAIN, 16));
            charges.add(app.getDrillingType().getDownPayment() + FeeCalculator.calculateDepthCharges(app.getEstimatedDepth()));
            
            gbc.weightx = 0;
            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.WEST;
            subTotalPanel.add(subTotal, gbc);
            costPanel.add(subTotalPanel);
            costPanel.add(Box.createVerticalStrut(25));
        }

        if (app.isNeedsPump()) {
            JPanel pumpLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            pumpLabelPanel.setOpaque(false);
            pumpLabelPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

            JLabel pumpLabel = new JLabel(app.getPumpType().name() + " Pump (" + Double.toString(app.getEstimatedDepth()) + "m):");
            pumpLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            pumpLabelPanel.add(pumpLabel);
            costPanel.add(pumpLabelPanel);

            JPanel paymentPanel = new JPanel(new GridBagLayout());
            paymentPanel.setPreferredSize(new Dimension(500, 30));
            paymentPanel.setMaximumSize(new Dimension(500, 30));
            paymentPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            paymentPanel.setOpaque(false);

            JLabel baseCostLabel = new JLabel("- Base Cost:");
            baseCostLabel.setFont(new Font("Calibri", Font.PLAIN, 16));

            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0;
            gbc.gridx = 0;
            paymentPanel.add(baseCostLabel, gbc);

            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            paymentPanel.add(Box.createHorizontalGlue(), gbc);

            JLabel baseCost = new JLabel("KES " + Double.toString(app.getPumpType().getBaseCost()));
            baseCost.setFont(new Font("Calibri", Font.PLAIN, 16));
            
            gbc.weightx = 0;
            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.WEST;
            paymentPanel.add(baseCost, gbc);
            costPanel.add(paymentPanel);
           
            JPanel depthPanel = new JPanel(new GridBagLayout());
            depthPanel.setPreferredSize(new Dimension(500, 30));
            depthPanel.setMaximumSize(new Dimension(500, 30));
            depthPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            depthPanel.setOpaque(false);

            JLabel depthChargesLabel = new JLabel("- Depth Charges (" + Double.toString(app.getEstimatedDepth()) + "m):");
            depthChargesLabel.setFont(new Font("Calibri", Font.PLAIN, 16));

            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0;
            gbc.gridx = 0;
            depthPanel.add(depthChargesLabel, gbc);

            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            depthPanel.add(Box.createHorizontalGlue(), gbc);

            JLabel depthCharges = new JLabel("KES " + Double.toString(FeeCalculator.calculateDepthCharges(app.getEstimatedDepth())));
            depthCharges.setFont(new Font("Calibri", Font.PLAIN, 16));
            
            gbc.weightx = 0;
            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.WEST;
            depthPanel.add(depthCharges, gbc);

            costPanel.add(depthPanel);

            JPanel heightPanel = new JPanel(new GridBagLayout());
            heightPanel.setPreferredSize(new Dimension(500, 30));
            heightPanel.setMaximumSize(new Dimension(500, 30));
            heightPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            heightPanel.setOpaque(false);

            JLabel heightLabelPanel = new JLabel("- Height Charges (" + Double.toString(app.getTankHeight()) + "m):");
            heightLabelPanel.setFont(new Font("Calibri", Font.PLAIN, 16));

            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0;
            gbc.gridx = 0;
            heightPanel.add(heightLabelPanel, gbc);

            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            heightPanel.add(Box.createHorizontalGlue(), gbc);

            JLabel heightCharges = new JLabel("KES " + Double.toString(FeeCalculator.calculateHeightCharges(app.getTankHeight())));
            heightCharges.setFont(new Font("Calibri", Font.PLAIN, 16));
            
            gbc.weightx = 0;
            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.WEST;
            heightPanel.add(heightCharges, gbc);
            costPanel.add(heightPanel);

            JPanel subTotalPanel = new JPanel(new GridBagLayout());
            subTotalPanel.setPreferredSize(new Dimension(500, 30));
            subTotalPanel.setMaximumSize(new Dimension(500, 30));
            subTotalPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            subTotalPanel.setOpaque(false);

            JLabel subTotalLabel = new JLabel("Subtotal Pump Installation:");
            subTotalLabel.setFont(new Font("Calibri", Font.PLAIN, 16));

            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0;
            gbc.gridx = 0;
            subTotalPanel.add(subTotalLabel, gbc);

            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            subTotalPanel.add(Box.createHorizontalGlue(), gbc);

            JLabel subTotal = new JLabel("KES " + Double.toString(app.getPumpType().getBaseCost() + FeeCalculator.calculateDepthCharges(app.getEstimatedDepth()) + FeeCalculator.calculateHeightCharges(app.getTankHeight())));
            subTotal.setFont(new Font("Calibri", Font.PLAIN, 16));
            charges.add(app.getPumpType().getBaseCost() + FeeCalculator.calculateDepthCharges(app.getEstimatedDepth()) + FeeCalculator.calculateHeightCharges(app.getTankHeight()));
            
            gbc.weightx = 0;
            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.WEST;
            subTotalPanel.add(subTotal, gbc);
            costPanel.add(subTotalPanel);
            costPanel.add(Box.createVerticalStrut(25));
        }

        if (app.isNeedsTank()) {
            JPanel tankLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            tankLabelPanel.setOpaque(false);
            tankLabelPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

            JLabel tankLabel = new JLabel("Tank Installation:");
            tankLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            tankLabelPanel.add(tankLabel);
            costPanel.add(tankLabelPanel);

            JPanel tankPanel = new JPanel(new GridBagLayout());
            tankPanel.setPreferredSize(new Dimension(500, 30));
            tankPanel.setMaximumSize(new Dimension(500, 30));
            tankPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            tankPanel.setOpaque(false);

            JLabel tankChargesLabel = new JLabel("Capacity Charges (" + Double.toString(app.getTankCapacity()) + "l):");
            tankChargesLabel.setFont(new Font("Calibri", Font.PLAIN, 16));

            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0;
            gbc.gridx = 0;
            tankPanel.add(tankChargesLabel, gbc);

            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            tankPanel.add(Box.createHorizontalGlue(), gbc);

            JLabel capacityCharges = new JLabel("KES " + Double.toString(app.getTankCapacity() * 50));
            capacityCharges.setFont(new Font("Calibri", Font.PLAIN, 16));
            charges.add(app.getTankCapacity() * 50);
            
            gbc.weightx = 0;
            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.WEST;
            tankPanel.add(capacityCharges, gbc);

            costPanel.add(tankPanel);
            costPanel.add(Box.createVerticalStrut(25));
        }

        if (app.isNeedsPlumbing()) {
            JPanel plumbingLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            plumbingLabelPanel.setOpaque(false);
            plumbingLabelPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

            JLabel plumbingLabel = new JLabel(app.getPipeType().name() + " Pipe (" + Double.toString(app.getPipeLength()) + "m):");
            plumbingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            plumbingLabelPanel.add(plumbingLabel);
            costPanel.add(plumbingLabelPanel);

            JPanel typePanel = new JPanel(new GridBagLayout());
            typePanel.setPreferredSize(new Dimension(500, 30));
            typePanel.setMaximumSize(new Dimension(500, 30));
            typePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            typePanel.setOpaque(false);

            JLabel baseCostLabel = new JLabel("- Pipe Type Cost:");
            baseCostLabel.setFont(new Font("Calibri", Font.PLAIN, 16));

            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0;
            gbc.gridx = 0;
            typePanel.add(baseCostLabel, gbc);

            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            typePanel.add(Box.createHorizontalGlue(), gbc);

            JLabel baseCost = new JLabel("KES " + Double.toString(app.getPipeType().getAdditionalCost()));
            baseCost.setFont(new Font("Calibri", Font.PLAIN, 16));
            
            gbc.weightx = 0;
            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.WEST;
            typePanel.add(baseCost, gbc);
            costPanel.add(typePanel);
           
            JPanel diameterPanel = new JPanel(new GridBagLayout());
            diameterPanel.setPreferredSize(new Dimension(500, 30));
            diameterPanel.setMaximumSize(new Dimension(500, 30));
            diameterPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            diameterPanel.setOpaque(false);

            JLabel diameterChargesLabel = new JLabel("- Diameter Charges (" + Double.toString(app.getPipeDiameter()) + "inches):");
            diameterChargesLabel.setFont(new Font("Calibri", Font.PLAIN, 16));

            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0;
            gbc.gridx = 0;
            diameterPanel.add(diameterChargesLabel, gbc);

            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            diameterPanel.add(Box.createHorizontalGlue(), gbc);

            JLabel diameterCharges = new JLabel("KES " + Double.toString(FeeCalculator.calculateDiamterCharges(app.getPipeDiameter(), app.getPipeLength())));
            diameterCharges.setFont(new Font("Calibri", Font.PLAIN, 16));
            
            gbc.weightx = 0;
            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.WEST;
            diameterPanel.add(diameterCharges, gbc);

            costPanel.add(diameterPanel);

            JPanel outletPanel = new JPanel(new GridBagLayout());
            outletPanel.setPreferredSize(new Dimension(500, 30));
            outletPanel.setMaximumSize(new Dimension(500, 30));
            outletPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            outletPanel.setOpaque(false);

            JLabel outletLabelPanel = new JLabel("- Outlet Charges (" + Double.toString(app.getNumberOfOutlets()) + "):");
            outletLabelPanel.setFont(new Font("Calibri", Font.PLAIN, 16));

            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0;
            gbc.gridx = 0;
            outletPanel.add(outletLabelPanel, gbc);

            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            outletPanel.add(Box.createHorizontalGlue(), gbc);

            JLabel outletCharges = new JLabel("KES " + Double.toString(FeeCalculator.calculateOutletCharges(app.getNumberOfOutlets())));
            outletCharges.setFont(new Font("Calibri", Font.PLAIN, 16));
            
            gbc.weightx = 0;
            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.WEST;
            outletPanel.add(outletCharges, gbc);
            costPanel.add(outletPanel);

            JPanel subTotalPanel = new JPanel(new GridBagLayout());
            subTotalPanel.setPreferredSize(new Dimension(500, 30));
            subTotalPanel.setMaximumSize(new Dimension(500, 30));
            subTotalPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            subTotalPanel.setOpaque(false);

            JLabel subTotalLabel = new JLabel("Subtotal Plumbing:");
            subTotalLabel.setFont(new Font("Calibri", Font.PLAIN, 16));

            gbc.anchor = GridBagConstraints.EAST;
            gbc.weightx = 0;
            gbc.gridx = 0;
            subTotalPanel.add(subTotalLabel, gbc);

            gbc.weightx = 1;
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            subTotalPanel.add(Box.createHorizontalGlue(), gbc);

            JLabel subTotal = new JLabel("KES " + Double.toString(app.getPipeType().getAdditionalCost() + FeeCalculator.calculateDepthCharges(app.getEstimatedDepth()) + FeeCalculator.calculateDiamterCharges(app.getPipeDiameter(), app.getPipeLength()) + FeeCalculator.calculateOutletCharges(app.getNumberOfOutlets())));
            subTotal.setFont(new Font("Calibri", Font.PLAIN, 16));
            charges.add(app.getPipeType().getAdditionalCost() + FeeCalculator.calculateDepthCharges(app.getEstimatedDepth()) + FeeCalculator.calculateDiamterCharges(app.getPipeDiameter(), app.getPipeLength()) + FeeCalculator.calculateOutletCharges(app.getNumberOfOutlets()));
            
            gbc.weightx = 0;
            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.WEST;
            subTotalPanel.add(subTotal, gbc);
            costPanel.add(subTotalPanel);
            costPanel.add(Box.createVerticalStrut(25));
        }

        JPanel subTotalPanel = new JPanel(new GridBagLayout());
        subTotalPanel.setPreferredSize(new Dimension(500, 30));
        subTotalPanel.setMaximumSize(new Dimension(500, 30));
        subTotalPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        subTotalPanel.setOpaque(false);

        JLabel subTotalLabel = new JLabel("SUBTOTAL:");
        subTotalLabel.setFont(new Font("Calibri", Font.PLAIN, 16));

        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        gbc.gridx = 0;
        subTotalPanel.add(subTotalLabel, gbc);

        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        subTotalPanel.add(Box.createHorizontalGlue(), gbc);

        Double allSubTotal = charges.stream().mapToDouble(Double::doubleValue).sum();

        JLabel subTotal = new JLabel("KES " + Double.toString(allSubTotal));
        subTotal.setFont(new Font("Calibri", Font.PLAIN, 16));
        
        gbc.weightx = 0;
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        subTotalPanel.add(subTotal, gbc);
        costPanel.add(subTotalPanel);

        JPanel taxPanel = new JPanel(new GridBagLayout());
        taxPanel.setPreferredSize(new Dimension(500, 30));
        taxPanel.setMaximumSize(new Dimension(500, 30));
        taxPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        taxPanel.setOpaque(false);

        JLabel taxLabel = new JLabel("TAX (16%):");
        taxLabel.setFont(new Font("Calibri", Font.PLAIN, 16));

        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        gbc.gridx = 0;
        taxPanel.add(taxLabel, gbc);

        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        taxPanel.add(Box.createHorizontalGlue(), gbc);

        JLabel tax = new JLabel("KES " + Double.toString(allSubTotal * 0.16));
        tax.setFont(new Font("Calibri", Font.PLAIN, 16));
        
        gbc.weightx = 0;
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        taxPanel.add(tax, gbc);
        costPanel.add(taxPanel);
        costPanel.add(Box.createVerticalStrut(25));

        JPanel estimatePanel = new JPanel(new GridBagLayout());
        estimatePanel.setPreferredSize(new Dimension(500, 30));
        estimatePanel.setMaximumSize(new Dimension(500, 30));
        estimatePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        estimatePanel.setOpaque(false);

        JLabel estimateLabel = new JLabel("TOTAL ESTIMATED COST:");
        estimateLabel.setFont(new Font("Calibri", Font.PLAIN, 16));

        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        gbc.gridx = 0;
        estimatePanel.add(estimateLabel, gbc);

        gbc.weightx = 1;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        estimatePanel.add(Box.createHorizontalGlue(), gbc);

        JLabel estimatedCost = new JLabel("KES " + Double.toString(allSubTotal * 1.16));
        estimatedCost.setFont(new Font("Calibri", Font.PLAIN, 16));
        
        gbc.weightx = 0;
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        estimatePanel.add(estimatedCost, gbc);
        costPanel.add(estimatePanel);

        mainPanel.add(costPanel);

        quoteFrame.getContentPane().add(navBar, BorderLayout.NORTH);
        quoteFrame.getContentPane().add(scrollBar, BorderLayout.CENTER);
        quoteFrame.setVisible(true);
        quoteFrame.setLocationRelativeTo(null);
    }

    private void confirmationFrame(Application app){
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(950, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<Image> icons = new ArrayList<>();
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon16X16.png")).getImage());
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon32X32.png")).getImage());
        icons.add(new ImageIcon(Dashboard.class.getResource("/Resources/icon64X64.png")).getImage());

        frame.setIconImages(icons);

        JPanel navBar = Dashboard.navBarPanel("APPLICATION SUBMITTED SUCCESSFULLY", frame);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        mainPanel.setBackground(new Color(236, 240, 241));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel successPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        successPanel.setOpaque(false);
        successPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel successIcon = new JLabel("\uf274");
        successIcon.setFont(Dashboard.getFontAwesome().deriveFont(Font.BOLD, 30f));
        successIcon.setForeground(new Color(96, 147, 93));
        successPanel.add(successIcon);

        JLabel successLabel = new JLabel("SUCCESS!");
        successLabel.setFont(new Font("Cambria", Font.BOLD, 20));
        successLabel.setForeground(new Color(44, 62, 80));
        successPanel.add(successLabel);
        mainPanel.add(successPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        JLabel subTitleLabel = new JLabel("Your application has been submitted");
        subTitleLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
        subTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(subTitleLabel);
        mainPanel.add(Box.createVerticalStrut(25));

        JPanel detailsPanel = new JPanel();
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(30, 20, 30, 20)
        ));
        detailsPanel.setLayout(new GridBagLayout());
        detailsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel appIDLabel = new JLabel("Application ID:");
        appIDLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        appIDLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        detailsPanel.add(appIDLabel, gbc);

        JLabel appID = new JLabel(app.getApplicationNumber());
        appID.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        appID.setForeground(new Color(52, 73, 94));
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        detailsPanel.add(appID, gbc);

        JLabel clientNameLabel = new JLabel("Client Name:");
        clientNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        clientNameLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        detailsPanel.add(clientNameLabel, gbc);

        JLabel clientName = new JLabel(app.getClient().getUsername());
        clientName.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        clientName.setForeground(new Color(52, 73, 94));
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        detailsPanel.add(clientName, gbc);

        JLabel dateSubmittedLabel = new JLabel("Date Submitted:");
        dateSubmittedLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dateSubmittedLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        detailsPanel.add(dateSubmittedLabel, gbc);

        JLabel dateSubmitted = new JLabel(app.getSubmittedDate().toLocalDateTime().toLocalDate().toString());
        dateSubmitted.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        dateSubmitted.setForeground(new Color(52, 73, 94));
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        detailsPanel.add(dateSubmitted, gbc);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        statusLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        detailsPanel.add(statusLabel, gbc);

        JLabel status = new JLabel(app.getStatus() + " REVIEW");
        status.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        status.setForeground(new Color(96, 147, 93));
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        detailsPanel.add(status, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        detailsPanel.add(Box.createVerticalStrut(15), gbc);

        JLabel estimatedCostLabel = new JLabel("ESTIMATED COST:");
        estimatedCostLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        estimatedCostLabel.setForeground(new Color(44, 62, 80));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        detailsPanel.add(estimatedCostLabel, gbc);

        JLabel estimatedCost = new JLabel("KES " + String.format("%.2f", app.getEstimatedCost()));
        estimatedCost.setFont(new Font("Segoe UI", Font.BOLD, 18));
        estimatedCost.setForeground(new Color(52, 152, 219));
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        detailsPanel.add(estimatedCost, gbc);
        mainPanel.add(detailsPanel);
        mainPanel.add(Box.createVerticalStrut(25));

        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setBackground(Color.WHITE);
        instructionsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(25, 20, 25, 20)
        ));
        instructionsPanel.setLayout(new BoxLayout(instructionsPanel, BoxLayout.Y_AXIS));
        instructionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
        instructionsPanel.setPreferredSize(new Dimension(800, 220));

        JLabel instructionLabel = new JLabel("What happens next?");
        instructionLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        instructionLabel.setForeground(new Color(44, 62, 80));
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        instructionsPanel.add(instructionLabel);
        instructionsPanel.add(Box.createVerticalStrut(12));

        String[] instructions = {"Our team will review your application within 24 hours", "A site survey will be scheduled", "You will receive a formal quotation", "Upon approval, work will commence"};
        for (int i = 0; i < instructions.length; i++) {
            JLabel instruction = new JLabel((i+1) + ". " + instructions[i]);
            instruction.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            instruction.setForeground(new Color(52, 73, 94));
            instruction.setAlignmentX(Component.LEFT_ALIGNMENT);
            instructionsPanel.add(instruction);
            if (i < instructions.length - 1) {
                instructionsPanel.add(Box.createVerticalStrut(8));
            }
        }
        mainPanel.add(instructionsPanel);
        mainPanel.add(Box.createVerticalStrut(25));

        RoundedButton anotherBtn = new RoundedButton("Submit Another", 15);
        anotherBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        anotherBtn.setBackground(new Color(96, 147, 93));
        anotherBtn.setForeground(Color.WHITE);
        anotherBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        anotherBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        anotherBtn.addActionListener(e -> applicationForm());
        mainPanel.add(anotherBtn);
        
        JPanel footer = Dashboard.footerPanel();

        frame.getContentPane().add(navBar, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(footer, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
