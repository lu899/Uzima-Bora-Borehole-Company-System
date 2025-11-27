package model;

import java.time.LocalDate;
import util.FeeCalculator;

public class PlumbingService extends Service{
    private PipeType pipeType;
    private double pipeDiameter;
    private double pipeLength;
    private int numOfOutlets;
    private double baseCost;
    private double diameterCharges;
    private double outletCharges;
    private double totalCost;

    public PlumbingService(int clientId, PipeType pipeType, double pipeDiameter, double pipeLength, int numOfOutlets, LocalDate date){
        super();
        this.clientId = clientId;
        this.serviceDate = date;
        this.numOfOutlets = numOfOutlets;
        this.pipeDiameter = pipeDiameter;
        this.pipeLength = pipeLength;
        this.pipeType = pipeType;
        this.baseCost = pipeType.getAdditionalCost() * pipeLength;
        this.diameterCharges = calculateDiamterCharges();
        this.outletCharges = calculateOutletCharges();
    }

    public void setPipeDiameter(double diameter){
        this.pipeDiameter = diameter;
    }

    public double getPipeDiameter(){
        return pipeDiameter;
    }

    public void setpipeLength(double length){
        this.pipeLength = length;
    }

    public double getPipeLength(){
        return pipeLength;
    }

    public void setNoOfOutlets(int outlets){
        this.numOfOutlets = outlets;
    }

    public int getNumOfOutlets(){
        return numOfOutlets;
    }

    public void setPipeType(PipeType type){
        this.pipeType = type;
    }

    public PipeType getPipeType(){
        return pipeType;
    }

    public double getTotalCost(){
        return totalCost;
    }

    public double calculateDiamterCharges(){
        return FeeCalculator.calculateDiamterCharges(pipeDiameter, pipeLength);
    }

    public double calculateOutletCharges(){
        return FeeCalculator.calculateOutletCharges(numOfOutlets);
    }

    public double calculateCost(){
        totalCost = baseCost + diameterCharges + outletCharges;
        return totalCost;
    }

    public String getServiceType(){
        return "Plumbing Service";
    }
}
