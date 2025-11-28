package model;

import java.time.LocalDate;

public abstract class Service {
    protected int serviceId;
    protected int clientId;
    protected LocalDate serviceDate;
    protected double totalCost;

    public Service(){
        super();
    }

    public void setServiceID(int serviceId){
        this.serviceId = serviceId;
    }

    public int getServiceID(){
        return serviceId;
    }

    public void setClientId(int clientId){
        this.clientId = clientId;
    }

    public int getClientId(){
        return clientId;
    }

    public void setTotalcost(double totalCost){
        this.totalCost = totalCost;
    }

    public double getTotalCost(){
        return totalCost;
    }

    public void setServiceDate(LocalDate serviceDate){
        this.serviceDate = serviceDate;
    }

    public LocalDate getServiceDate(){
        return serviceDate;
    }

    public abstract double calculateCost();
    public abstract String getServiceType();
}
