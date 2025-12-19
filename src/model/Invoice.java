package model;

import java.time.LocalDate;
import java.util.*;

public class Invoice {
    private int invoiceId;
    private String invoiceNumber;
    private Client client;
    private double surveyFees;
    private double localAuthorityFees;
    private double drillingTotal;
    private double pumpTotal;
    private double tankTotal;
    private double plumbingTotal;
    private double subtotal;
    private double taxAmount;
    private double grandTotal;
    private LocalDate invoiceDate;
    private PaymentStatus paymentStatus;
    private double amountPaid;
    private String serviceType;

    private List<Service> drillingServices;
    private List<Service> pumpInstallations;
    private List<Service> tankInstallations;
    private List<Service> plumbingServices;

    public enum PaymentStatus{
        UNPAID("Unpaid"),
        PARTIAL("Partial"),
        PAID("Paid");

        private String displayName;

        PaymentStatus(String displayName){
            this.displayName = displayName;
        }

        public String getDisplayName(){
            return displayName;
        }
    }

    public Invoice(Client client){
        this.client = client;
        this.surveyFees = client.getClientCategory().getSurveyFees();
        this.localAuthorityFees = client.getClientCategory().getLocalAuthorityFees();
        this.invoiceDate = LocalDate.now();
        this.paymentStatus = PaymentStatus.UNPAID;
        this.amountPaid = 0.0;
        this.drillingTotal = 0.0;
        this.pumpTotal = 0.0;
        this.plumbingTotal = 0.0;
        this.tankTotal = 0.0;

        this.drillingServices = new ArrayList<>();
        this.pumpInstallations = new ArrayList<>();
        this.tankInstallations = new ArrayList<>();
        this.plumbingServices = new ArrayList<>();
    }

    public void addService(Service service){
        if (service == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }

        if (service instanceof DrillingService) {
            DrillingService drilling = (DrillingService) service;
            drillingServices.add(drilling);
            drillingTotal += drilling.calculateCost();
        } else if (service instanceof PlumbingService) {
            PlumbingService plumbing = (PlumbingService) service;
            plumbingServices.add(plumbing);
            plumbingTotal += plumbing.calculateCost();
        } else if (service instanceof TankInstallation) {
            TankInstallation tank = (TankInstallation) service;
            tankInstallations.add(tank);
            tankTotal += tank.calculateCost();
        } else if (service instanceof PumpInstallation) {
            PumpInstallation pump = (PumpInstallation) service;
            pumpInstallations.add(pump);
            pumpTotal += pump.calculateCost();
        } else {
            throw new IllegalArgumentException("Unknoun service type: " + service.getClass().getName());
        }

        calculateTotals();
    }

    public void addServices(List<Service> services){
        if (services == null || services.isEmpty()) {
            return;
        }

        for (Service service : services) {
            addService(service);
        }
    }

    public double calculateSubtotal(){
        subtotal = surveyFees + localAuthorityFees + drillingTotal + pumpTotal + tankTotal + plumbingTotal;
        return subtotal;
    }

    public double calculateTax(){
        taxAmount = calculateSubtotal() * 0.16;
        return taxAmount;
    }

    public double calculateGrandTotal(){
        grandTotal = calculateSubtotal() + calculateTax();
        return grandTotal;
    }

    public void calculateTotals(){
        calculateSubtotal();
        calculateTax();
        calculateGrandTotal();
    }

    public void generateInvoice(){
        calculateTotals();;

        if (invoiceNumber == null || invoiceNumber.isEmpty()) {
            invoiceNumber = generateInvoiceNumber();
        }
    }

    private String generateInvoiceNumber(){
        int year = LocalDate.now().getYear();
        int randomNum = (int) (Math.random() * 1000);
        return String.format("INV-%d-%03d", year, randomNum);
    }

    public void recordPayment(double amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }

        amountPaid += amount;

        if (amountPaid >= grandTotal) {
            paymentStatus = PaymentStatus.PAID;
            amountPaid = grandTotal;
        } else if (amountPaid > 0) {
            paymentStatus = PaymentStatus.PARTIAL;
        }
    }

    public double getOutstandingAmount(){
        return Math.max(0, grandTotal - amountPaid);
    }

    public boolean isPaid(){
        return paymentStatus == PaymentStatus.PAID;
    }

    public void setInvoiceId(int invoiceId){
        this.invoiceId = invoiceId;
    }
    
    public int getInvoiceId(){
        return invoiceId;
    }

    public void setInvoiceNubmer(String num){
        this.invoiceNumber = num;
    }

    public String getInvoiceNumber(){
        return invoiceNumber;
    }

    public void setClient(Client client){
        this.client = client;
    }
    
    public Client getClient(){
        return client;
    }

    public void setAmountPaid(double amountPaid){
        this.amountPaid = amountPaid;
    }

    public double getAmountPaid(){
        return amountPaid;
    }

    public double getSurveyFees(){
        return surveyFees;
    }

    public double getLocalAuthorityFees(){
        return localAuthorityFees;
    }

    public double getDrillingTotal(){
        return drillingTotal;
    }

    public double getPumpTotal(){
        return pumpTotal;
    }
    public double getTankTotal(){
        return tankTotal;
    }

    public void setServiceType(String type){
        this.serviceType = type;
    }

    public String getServiceType(){
        return serviceType;
    }
    public double getPlumbingTotal(){
        return plumbingTotal;
    }
    public double getGrandTotal(){
        return grandTotal;
    }
    public void setGrandTotal(double total){
        this.grandTotal = total;
    }
    public void setInvoiceDate(LocalDate date){
        this.invoiceDate = date;
    }
    public LocalDate getInvoiceDate(){
        return invoiceDate;
    }
    public void setStatus(PaymentStatus status){
        this.paymentStatus = status;
    }

    public PaymentStatus getPaymentStatus(){
        return paymentStatus;
    }
}
