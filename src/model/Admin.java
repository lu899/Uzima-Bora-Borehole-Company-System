package model;

public class Admin extends User{
    private boolean isActive;

    public Admin(String name, String email){
        super(name, email);
    }

    public void setIsActive(boolean isActive){
        this.isActive = isActive;
    }
    public boolean getIsActive(){
        return isActive;
    }
}
