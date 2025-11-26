package model;

import java.util.*;

public abstract class Service {
    private int serviceId;
    private int clientId;
    private Date serviceDate;
    private double totalCost;

    public Service(){
        super();
    }

    public int getServiceID(){
        return this.serviceId;
    }

    public int getClientId(){
        return this.clientId;
    }

    public double getTotalcost(){
        return totalCost;
    }

    public double calculateCost(){
        double total = 0;
        return total;
    }
}
