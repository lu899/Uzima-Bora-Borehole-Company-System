package model;

import java.time.LocalDate;

public class TankInstallation extends Service{
    private double capcityLitres;
    private double costPerLitre;

    public TankInstallation(int clientId, double capcityLitres, double cost, LocalDate date){
        super();
        this.costPerLitre = cost;
        this.clientId = clientId;
        this.serviceDate = date;
        this.capcityLitres = capcityLitres;
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
        return capcityLitres * costPerLitre;
    }

    public String getServiceType(){
        return "Tank Installation";
    }
}
