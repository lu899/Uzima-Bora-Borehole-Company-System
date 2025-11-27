package model;

import java.time.LocalDate;
import util.FeeCalculator;

public class PumpInstallation extends Service{
    private PumpType pumpType;
    private final double baseCost;
    private double boreholeDepth;
    private double tankHeight;
    private double depthCharges;
    private double heightCharges;

    public PumpInstallation(int clientId, PumpType pumpType, double boreholeDepth, double tankHeight, LocalDate date){
        super();
        this.clientId = clientId;
        this.pumpType = pumpType;
        this.baseCost = pumpType.getBaseCost();
        this.boreholeDepth = boreholeDepth;
        this.tankHeight = tankHeight;
        this.serviceDate = date;
        this.depthCharges = calculateDepthCharges();
        this.heightCharges = calculateHeightCharges();
    }

    public double getBaseCost(){
        return baseCost;
    }

    public void setBoreholeDepth(int depth){
        this.boreholeDepth = depth;
    }

    public double getBoreholeDepth(){
        return boreholeDepth;
    }

    public void setTankHeight(int height){
        this.tankHeight = height;
    }

    public double getTankHeight(){
        return tankHeight;
    }

    public double calculateDepthCharges(){
        return FeeCalculator.calculateDepthCharges(boreholeDepth);
    }

    public double calculateHeightCharges(){
        return FeeCalculator.calculateHeightCharges(tankHeight);
    }

    public double calculateCost(){
        return baseCost + depthCharges + heightCharges;
    }

    public PumpType getPumpType(){
        return pumpType;
    }

    public String getServiceType(){
        return "Pump Installation";
    }
}
