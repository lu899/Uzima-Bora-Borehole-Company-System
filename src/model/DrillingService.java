package model;

import java.time.LocalDate;
import util.FeeCalculator;

public class DrillingService extends Service{
    private DrilingType drillingType;
    private double boreholeDepth;
    private double depthCharges;
    private final double downPayment;

    public DrillingService(int clientId, DrilingType drillingType, double depth, LocalDate date){
        super();
        this.clientId = clientId;
        this.drillingType = drillingType;
        this.boreholeDepth = depth;
        this.serviceDate = date;
        this.downPayment = drillingType.getDownPayment();
        this.depthCharges = calculateDepthCharges();
        this.totalCost = calculateCost();
    }

    public void setDrillingType(DrilingType drilingType){
        this.drillingType = drilingType;
    }

    public DrilingType getDrilingType(){
        return drillingType;
    }

    public void setBoreholeDepth(double boreholeDepth){
       this.boreholeDepth = boreholeDepth;
    }

    public double getBoreholeDepth(){
        return this.boreholeDepth;
    }

    public void setDepthCharges(double depthCharges){
        this.depthCharges = depthCharges;
    }

    public double getDepthCharges(){
       return this.depthCharges;
    }

    public double getDownPayment(){
        return downPayment;
    }

    public double calculateDepthCharges(){
        return FeeCalculator.calculateDepthCharges(boreholeDepth);
    }

    public double calculateCost(){
        return downPayment + depthCharges;
    }

    public String getServiceType(){
        return "Drilling Service";
    }
}
