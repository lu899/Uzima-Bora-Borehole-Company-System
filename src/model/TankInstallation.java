package model;

import java.time.LocalDate;

public class TankInstallation extends Service{
    private double capcityLitres;
    private double costPerLitre = 50;

    public TankInstallation(int clientId, double capcityLitres, LocalDate date){
        super();
        this.clientId = clientId;
        this.serviceDate = date;
        this.capcityLitres = capcityLitres;
        this.totalCost = calculateCost();
    }

    public double getCapacity(){
        return capcityLitres;
    }

    public void setCapacity(double capacity){
        this.capcityLitres = capacity;
    }

    public double getCostPerLitre(){
        return costPerLitre;
    }

    public void setCostPerLitre(int cost){
        this.costPerLitre = cost;
    }

    public double calculateCost(){
        totalCost = capcityLitres * costPerLitre;
        return totalCost;
    }

    public String getServiceType(){
        return "Tank Installation";
    }
}
