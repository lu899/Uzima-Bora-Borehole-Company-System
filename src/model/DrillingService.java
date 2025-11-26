package model;

public class DrillingService extends Service{
    private DrilingType drillingType;
    private double boreholeDepth;
    private double depthCharges;

    public DrillingService(DrilingType drillingType){
        super();
        this.drillingType = drillingType;
    }

    public double getDownPayment(){
        return drillingType.getDownPayment();
    }

    public double calculateDepthCharges(){
        return boreholeDepth * depthCharges;
    }

    public double calculateCost(){
        return getDownPayment() + calculateDepthCharges();
    }
}
