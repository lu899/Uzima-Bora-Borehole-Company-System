package Ui;
import java.awt.*;
import java.time.LocalDate;

import javax.swing.*;

import database.ClientDAO;
import model.Client;
import model.DrilingType;
import model.DrillingService;
import model.PipeType;
import model.PlumbingService;
import model.PumpInstallation;
import model.PumpType;
import model.Service;
import model.TankInstallation;

public class ServiceRegistration {
    private static ClientDAO clientDAO = new ClientDAO();
    private static String drillingSelection = "0.0";
    private static String pumpSelection = "0.0";

    public static void applicationForm(Client client){
        JFrame formFrame = new JFrame("UZIMA BORA BOREHOLE SYSTEM");
        formFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        formFrame.setSize(new Dimension(900, 650));
        formFrame.setLayout(new BorderLayout());

        JPanel navBar = Dashboard.navBarPanel("APPLY FOR SERVICE", formFrame);

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
        formPanel.setPreferredSize(new Dimension(450, 650));
        formPanel.setMaximumSize(new Dimension(450, 650));
        formPanel.setBackground(new Color(218, 217, 232));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("SERVICES REQUIRED (Select all that apply)");
        title.setFont(new Font("CAMBRIA", Font.BOLD, 20));
        formPanel.add(title);
        formPanel.add(Box.createVerticalStrut(10));

        // Borehole Drilling

        JPanel boreholePanel = new JPanel();
        boreholePanel.setLayout(new BoxLayout(boreholePanel, BoxLayout.Y_AXIS));
        boreholePanel.setBackground(new Color(218, 217, 232));
        boreholePanel.setPreferredSize(new Dimension(500, 130));     
        boreholePanel.setMaximumSize(new Dimension(500, 130));
        boreholePanel.setAlignmentX(Component.LEFT_ALIGNMENT);  

        JCheckBox boreholeCheckBox = new JCheckBox("BOREHOLE DRILLING");
        boreholeCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));    
        boreholeCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        boreholeCheckBox.setBackground(new Color(218, 217, 232));
        boreholePanel.add(boreholeCheckBox);
        boreholePanel.add(Box.createVerticalGlue());

        JPanel boreholeSelectionPanel = new JPanel();
        boreholeSelectionPanel.setBackground(Color.WHITE);
        boreholeSelectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        boreholeSelectionPanel.setLayout(new BoxLayout(boreholeSelectionPanel, BoxLayout.Y_AXIS));
        boreholeSelectionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        JPanel dRadioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        dRadioPanel.setBackground(Color.WHITE);
        dRadioPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel drillingLabel = new JLabel("Drilling Type");
        dRadioPanel.add(drillingLabel);

        ButtonGroup drillingGroup = new ButtonGroup();

        DrilingType[] radioTexts = {DrilingType.CORE, DrilingType.SYMMETRIC, DrilingType.GEO_TECHNICAL};
        JRadioButton[] radioButtons = new JRadioButton[radioTexts.length];

        for (int i=0; i<radioTexts.length; i++) {
            Double downPayment = radioTexts[i].getDownPayment();
            radioButtons[i] = new JRadioButton(radioTexts[i].name().toLowerCase());
            radioButtons[i].setActionCommand(Double.toString(downPayment));
            radioButtons[i].setBackground(Color.WHITE);
            radioButtons[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            drillingGroup.add(radioButtons[i]);
            dRadioPanel.add(radioButtons[i]);
        }
        boreholeSelectionPanel.add(dRadioPanel);
        
        JLabel downPaymentLabel = new JLabel("Down Payment: " + drillingSelection + " (Auto-calculated)");
        downPaymentLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        for (JRadioButton radioBtn : radioButtons) {
            radioBtn.addActionListener(e -> {
                String selectedPayment = drillingGroup.getSelection().getActionCommand();
                drillingSelection = radioBtn.getText().toUpperCase();
                downPaymentLabel.setText("Down Payment: KES " + selectedPayment + " (Auto-calculated)");
            });
        }

        JPanel depthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        depthPanel.setBackground(Color.WHITE);
        depthPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel depthLabel = new JLabel("Borehole Depth: ");
        JTextField depth = new JTextField(5);
        JLabel meters = new JLabel(" meters");
        depthPanel.add(depthLabel);
        depthPanel.add(depth);
        depthPanel.add(meters);
        boreholeSelectionPanel.add(depthPanel);
        boreholeSelectionPanel.add(Box.createVerticalStrut(5));
        boreholeSelectionPanel.add(downPaymentLabel);

        boreholePanel.add(boreholeSelectionPanel);
        formPanel.add(boreholePanel);
        formPanel.add(Box.createVerticalStrut(10));
        
        // Water Pump Installation

        JPanel pumpPanel = new JPanel();
        pumpPanel.setLayout(new BoxLayout(pumpPanel, BoxLayout.Y_AXIS));
        pumpPanel.setBackground(new Color(218, 217, 232));
        pumpPanel.setPreferredSize(new Dimension(500, 160));     
        pumpPanel.setMaximumSize(new Dimension(500, 160));
        pumpPanel.setAlignmentX(Component.LEFT_ALIGNMENT);   

        JCheckBox pumpCheckBox = new JCheckBox("WATER PUMP INSTALLATION");
        pumpCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));    
        pumpCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        pumpCheckBox.setBackground(new Color(218, 217, 232));
        pumpPanel.add(pumpCheckBox);

        JPanel pumpSelectionPanel = new JPanel();
       pumpSelectionPanel.setBackground(Color.WHITE);
       pumpSelectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
       pumpSelectionPanel.setLayout(new BoxLayout(pumpSelectionPanel, BoxLayout.Y_AXIS));
       pumpSelectionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        JPanel pumpRadioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pumpRadioPanel.setBackground(Color.WHITE);
        pumpRadioPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel pumpLabel = new JLabel("Pump Type");
        pumpRadioPanel.add(pumpLabel);

        ButtonGroup pumpGroup = new ButtonGroup();
        PumpType[] pumps = {PumpType.HAND, PumpType.SOLAR, PumpType.SUBMERSIBLE_ELECTRIC};
        JRadioButton[] pumpButtons = new JRadioButton[pumps.length];
        
        for (int i=0; i<pumps.length; i++) {
            Double baseCost = pumps[i].getBaseCost();
            pumpButtons[i] = new JRadioButton(pumps[i].name().toLowerCase());
            pumpButtons[i].setActionCommand(Double.toString(baseCost));
            pumpButtons[i].setBackground(Color.WHITE);
            pumpButtons[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            pumpGroup.add(pumpButtons[i]);
            pumpRadioPanel.add(pumpButtons[i]);
        }
        pumpSelectionPanel.add(pumpRadioPanel);

        JLabel baseCostLabel = new JLabel("Base Cost: " + pumpSelection + " (Auto-calculated)");
        baseCostLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (JRadioButton radioBtn : pumpButtons) {
            radioBtn.addActionListener(e -> {
                String selectedPayment = pumpGroup.getSelection().getActionCommand();
                pumpSelection = radioBtn.getText().toUpperCase();
                baseCostLabel.setText("Base Cost: " + selectedPayment + " (Auto-calculated)");
            });
        }

        JPanel tankDepthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        tankDepthPanel.setBackground(Color.WHITE);
        tankDepthPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tankDepthLabel = new JLabel("Borehole Depth: ");
        JTextField depth2 = new JTextField(5);
        JLabel meters2 = new JLabel(" meters");
        tankDepthPanel.add(tankDepthLabel);
        tankDepthPanel.add(depth2);
        tankDepthPanel.add(meters2);

        JPanel heightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        heightPanel.setBackground(Color.WHITE);
        heightPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heightLabel = new JLabel("Tank Height Above Ground: ");
        JTextField height = new JTextField(5);
        heightPanel.add(heightLabel);
        heightPanel.add(height);
        heightPanel.add(meters);
        pumpSelectionPanel.add(tankDepthPanel);
        pumpSelectionPanel.add(heightPanel);
        pumpSelectionPanel.add(Box.createVerticalStrut(5));

        pumpSelectionPanel.add(baseCostLabel);
        pumpPanel.add(pumpSelectionPanel);
        formPanel.add(pumpPanel);
        formPanel.add(Box.createVerticalStrut(10));

        // Tank Installation

        JPanel tankPanel = new JPanel();
        tankPanel.setLayout(new BoxLayout(tankPanel, BoxLayout.Y_AXIS));
        tankPanel.setBackground(new Color(218, 217, 232));
        tankPanel.setPreferredSize(new Dimension(500, 100));     
        tankPanel.setMaximumSize(new Dimension(500, 100));
        tankPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JCheckBox tankCheckBox = new JCheckBox("WATER TANK INSTALLATION");
        tankCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));    
        tankCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        tankCheckBox.setBackground(new Color(218, 217, 232));
        tankPanel.add(tankCheckBox);

        JPanel tankDetails = new JPanel();
        tankDetails.setBackground(Color.WHITE);
        tankDetails.setAlignmentX(Component.LEFT_ALIGNMENT);
        tankDetails.setLayout(new BoxLayout(tankDetails, BoxLayout.Y_AXIS));
        tankDetails.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        JPanel capacityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        capacityPanel.setBackground(Color.WHITE);
        capacityPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField capacity = new JTextField(5);
        JLabel capacityLabel = new JLabel("Tank Capacity: ");
        JLabel litresLabel = new JLabel(" litres");
        JLabel cost = new JLabel("Cost per Litre: KES 50.00");
        capacityPanel.add(capacityLabel);
        capacityPanel.add(capacity);
        capacityPanel.add(litresLabel);

        tankDetails.add(capacityPanel);
        tankDetails.add(cost);

        tankPanel.add(tankDetails);
        formPanel.add(tankPanel);
        formPanel.add(Box.createVerticalStrut(10));
        
        // Plumbing Services

        JPanel plumbingPanel = new JPanel();
        plumbingPanel.setLayout(new BoxLayout(plumbingPanel, BoxLayout.Y_AXIS));
        plumbingPanel.setBackground(new Color(218, 217, 232));
        plumbingPanel.setPreferredSize(new Dimension(500, 160));     
        plumbingPanel.setMaximumSize(new Dimension(500, 160));
        plumbingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JCheckBox plumbingCheckBox = new JCheckBox("PLUMBING SERVICES");
        plumbingCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));    
        plumbingCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        plumbingCheckBox.setBackground(new Color(218, 217, 232));
        plumbingPanel.add(plumbingCheckBox);

        JPanel plumbingDetails = new JPanel();
        plumbingDetails.setBackground(Color.WHITE);
        plumbingDetails.setAlignmentX(Component.LEFT_ALIGNMENT);
        plumbingDetails.setLayout(new BoxLayout(plumbingDetails, BoxLayout.Y_AXIS));
        plumbingDetails.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        typePanel.setBackground(Color.WHITE);
        typePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel pipetypeLabel = new JLabel("Pipe Type: ");
        PipeType[] pipeOptions = {PipeType.PVC, PipeType.HDPE, PipeType.STEEL, PipeType.GALVANIZED_STEEL, PipeType.STAINLESS_STEEL};
        JComboBox<PipeType> type = new JComboBox<>(pipeOptions);
        typePanel.add(pipetypeLabel);
        typePanel.add(type);
        plumbingDetails.add(typePanel);

        JPanel diameterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        diameterPanel.setBackground(Color.WHITE);
        diameterPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField diameter = new JTextField(5);
        JLabel diameterLabel = new JLabel("Diameter: ");
        JLabel inches = new JLabel(" inches");
        diameterPanel.add(diameterLabel);
        diameterPanel.add(diameter);
        diameterPanel.add(inches);
        plumbingDetails.add(diameterPanel);

        JPanel lengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        lengthPanel.setBackground(Color.WHITE);
        lengthPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField length = new JTextField(5);
        JLabel lengthLabel = new JLabel("Pipe Length: ");
        JLabel meters3 = new JLabel(" meters");
        lengthPanel.add(lengthLabel);
        lengthPanel.add(length);
        lengthPanel.add(meters3);
        plumbingDetails.add(lengthPanel);

        JPanel outletsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        outletsPanel.setBackground(Color.WHITE);
        outletsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField outlets = new JTextField(5);
        JLabel outletsLabel = new JLabel("Number of Outlets: ");
        outletsPanel.add(outletsLabel);
        outletsPanel.add(outlets);
        plumbingDetails.add(outletsPanel);

        plumbingPanel.add(plumbingDetails);
        formPanel.add(plumbingPanel);

        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> {
            if (boreholeCheckBox.isSelected() && drillingSelection != "0.0" && !checkEmptyTextField(boreholeSelectionPanel)) {
                DrillingService drillingService = new DrillingService(client.getClientId(), DrilingType.valueOf(drillingSelection), Double.parseDouble(depth.getText()), LocalDate.now().plusDays(14));
                clientDAO.insertDrillingService(drillingService);
            }

            if (pumpCheckBox.isSelected() && pumpSelection != "0.0" && !checkEmptyTextField(tankDetails)) {
              PumpInstallation pumpInstallation = new PumpInstallation(client.getClientId(), PumpType.valueOf(pumpSelection), Double.parseDouble(depth2.getText()), Double.parseDouble(height.getText()), LocalDate.now().plusDays(14));
              clientDAO.insertPumpService(pumpInstallation);  
            }

            if (tankCheckBox.isSelected() && capacity.getText() != null) {
                TankInstallation tankInstallation = new TankInstallation(client.getClientId(), Double.parseDouble(capacity.getText()), LocalDate.now().plusDays(14));
                clientDAO.insertTankService(tankInstallation);
            }

            if (plumbingCheckBox.isSelected() && checkEmptyTextField(plumbingDetails)) {
                PlumbingService plumbingService = new PlumbingService(client.getClientId(), (PipeType) type.getSelectedItem(), Double.parseDouble(diameter.getText()), Double.parseDouble(length.getText()), Integer.parseInt(outlets.getText()), LocalDate.now().plusDays(14));
                clientDAO.insertPlumbingService(plumbingService);
            }
        });

        mainPanel.add(formPanel);
        mainPanel.add(submit);

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

    // public static void appliedServices(JCheckBox checkBox, Client client, Service service){
        
    // }

    public static boolean checkEmptyTextField(JPanel panel){
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

    public static void setLabelFont(JPanel panel, Font font){
        for (Component component : panel.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                label.setFont(font);
            }
        }
    }
}
