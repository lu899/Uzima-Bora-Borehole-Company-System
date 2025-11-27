package model;

public enum PumpType {
    SUBMERSIBLE_ELECTRIC(90000),
    SOLAR(65000),
    HAND(30000);
    
   private final double baseCost;

   PumpType(double baseCost){
    this.baseCost = baseCost;
   }

   public double getBaseCost(){
    return baseCost;
   }
}
