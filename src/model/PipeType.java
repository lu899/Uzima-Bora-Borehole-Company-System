package model;

public enum PipeType {
    PVC(0),
    HDPE(100),
    STEEL(300),
    GALVANIZED_STEEL(400),
    STAINLESS_STEEL(800);
    
    private final int additionalCost;

    PipeType(int additionalCost){
        this.additionalCost = additionalCost;
    }

    public int getAdditionalCost(){
        return additionalCost;
    }
}
