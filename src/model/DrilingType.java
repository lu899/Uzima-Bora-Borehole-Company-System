package model;

public enum DrilingType {
    SYMMETRIC(130000),
    CORE(225000),
    GEO_TECHNICAL(335000);

    private final double downPayment;
    DrilingType(double downPayment){
        this.downPayment = downPayment;
    }

    public double getDownPayment(){
        return downPayment;
    }
}
