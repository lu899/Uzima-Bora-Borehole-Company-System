package model;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;

import database.DatabaseManager;
import util.FeeCalculator;

public class Application {
    private int applicationId;
    private String applicationNo;
    private Client client;

    private Boolean needsDrilling;
    private DrilingType drillingType;
    private double estimatedDepth;

    private Boolean needsPump;
    private PumpType pumpType;
    private double tankHeight;

    private Boolean needsTank;
    private double tankCapacity;

    private Boolean needsPlumbing;
    private PipeType pipeType;
    private double pipeDiameter;
    private double pipeLength;
    private int numOfOutlets;

    private String additionalNotes;
    private Double estimatedCost;
    private Status status;

    private Timestamp submittedDate;
    private Timestamp reviewedDate;
    private int reviewedBy;
    private String rejectionReason;

    public Application(Client client){
        this.applicationNo = generateApplicationNumber();
        this.client = client;
        this.status = Status.PENDING;
        this.submittedDate = Timestamp.valueOf(LocalDateTime.now());
        this.reviewedDate = getReviewedDate();
        this.reviewedBy = getReviewedBy();
        this.rejectionReason = getRejectionReason();
        this.needsDrilling = false;
        this.needsPlumbing = false;
        this.needsPump = false;
        this.needsTank = false;
    }

    private String generateApplicationNumber(){
        Connection con = DatabaseManager.getConnection();
        String query = "SELECT COUNT(*) FROM applications WHERE YEAR(submitted_date) = YEAR(CURDATE())";

        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                int count = rs.getInt(1) + 1;
                int year = LocalDate.now().getYear();
                return String.format("APP-%d-%03d", year, count);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error generating application number: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        return "APP-" + System.currentTimeMillis();
    }

    private double calculateEstimatedCost(){
        double total = 0.0;

        double surveyFees = client.getClientCategory().getSurveyFees();
        double authorityFees = client.getClientCategory().getLocalAuthorityFees();
        total += surveyFees + authorityFees;

        if (needsDrilling) {
            double downPayment = drillingType.getDownPayment();
            double depthCharges = FeeCalculator.calculateDepthCharges(estimatedDepth);
            total += downPayment + depthCharges;
        }

        if (needsPump) {
            double baseCost = pumpType.getBaseCost();
            double depthCharges = FeeCalculator.calculateDepthCharges(estimatedDepth);
            double heightCharges = FeeCalculator.calculateHeightCharges(tankHeight);
            total += baseCost + depthCharges + heightCharges;
        }

        if (needsTank) {
            total += tankCapacity * 50;
        }

        if (needsPlumbing) {
            double baseCost = pipeType.getAdditionalCost() * pipeLength;
            double diameterCost = FeeCalculator.calculateDiamterCharges(pipeDiameter, pipeLength);
            double outletCharges = FeeCalculator.calculateOutletCharges(numOfOutlets);
            total += baseCost + diameterCost + outletCharges;
        } 

        total = total * 1.16;
        return total;
    }

    public int getApplicationId() { return applicationId; }
    public void setApplicationId(int applicationId) { this.applicationId = applicationId; }
    
    public String getApplicationNumber() { return applicationNo; }
    public void setapplicationNumber(String applicationNo) { this.applicationNo = applicationNo; }

    public Client getClient() { return client; }
    public void setClient(Client client) {this.client = client;}
    
    public String getBoreholeLocation() { return client.getBoreholeLocation(); }
    public void setBoreholeLocation(String boreholeLocation) { client.setBoreholeLocation(boreholeLocation); }
    
    public boolean isNeedsDrilling() { return needsDrilling; }
    public void setNeedsDrilling(boolean needsDrilling) { this.needsDrilling = needsDrilling; }
    
    public DrilingType getDrillingType() { return drillingType; }
    public void setDrillingType(DrilingType drillingType) { this.drillingType = drillingType; }
    
    public double getEstimatedDepth() { return estimatedDepth; }
    public void setEstimatedDepth(double estimatedDepth) { this.estimatedDepth = estimatedDepth; }
    
    public boolean isNeedsPump() { return needsPump; }
    public void setNeedsPump(boolean needsPump) { this.needsPump = needsPump; }
    
    public PumpType getPumpType() { return pumpType; }
    public void setPumpType(PumpType pumpType) { this.pumpType = pumpType; }
    
    public double getTankHeight() { return tankHeight; }
    public void setTankHeight(double tankHeight) { this.tankHeight = tankHeight; }
    
    public boolean isNeedsTank() { return needsTank; }
    public void setNeedsTank(boolean needsTank) { this.needsTank = needsTank; }
    
    public double getTankCapacity() { return tankCapacity; }
    public void setTankCapacity(double tankCapacity) { this.tankCapacity = tankCapacity; }
    
    public boolean isNeedsPlumbing() { return needsPlumbing; }
    public void setNeedsPlumbing(boolean needsPlumbing) { this.needsPlumbing = needsPlumbing; }
    
    public PipeType getPipeType() { return pipeType; }
    public void setPipeType(PipeType pipeType) { this.pipeType = pipeType; }
    
    public double getPipeDiameter() { return pipeDiameter; }
    public void setPipeDiameter(double pipeDiameter) { this.pipeDiameter = pipeDiameter; }
    
    public double getPipeLength() { return pipeLength; }
    public void setPipeLength(double pipeLength) { this.pipeLength = pipeLength; }
    
    public int getNumberOfOutlets() { return numOfOutlets; }
    public void setNumberOfOutlets(int numberOfOutlets) { this.numOfOutlets = numberOfOutlets; }
    
    public String getAdditionalNotes() { return additionalNotes; }
    public void setAdditionalNotes(String additionalNotes) { this.additionalNotes = additionalNotes; }
    
    public double getEstimatedCost() { return estimatedCost; }
    public void setEstimatedCost() { this.estimatedCost = calculateEstimatedCost(); }

    public int getReviewedBy() { return reviewedBy; }
    public void setReviewedBy(int reviewedBy) { this.reviewedBy = reviewedBy; }
    
    public Timestamp getReviewedDate() { return reviewedDate; }
    public void setReviewedDate(Timestamp reviewedDate) { this.reviewedDate = reviewedDate; }

    public Timestamp getSubmittedDate() { return submittedDate; }
    public void setSubmittedDate(Timestamp submittedDate) { this.submittedDate = submittedDate; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
    
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
