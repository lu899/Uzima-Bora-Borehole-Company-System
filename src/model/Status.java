package model;

public enum Status {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    IN_PROGRESS("In Progress");
    
    private final String statusName;

    Status(String statusName){
        this.statusName = statusName;
    }
    
    public String getStatus(){
        return statusName;
    }
}
