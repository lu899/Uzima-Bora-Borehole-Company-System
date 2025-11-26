package model;

public class Client extends User{
    private String address;
    private String telephone;
    private String location;
    private ClientCategory category;

    public Client(String name, String address, String telephone, String email, String location, ClientCategory category){
        super(name, email);
        this.address = address;
        this.telephone = telephone;
        this.location = location;
        this.category = category;
    }

    public void setClientId(int id){
        this.setUserId(id);
    }

    public int getClientId(){
        return this.getUserId();
    }

    public void setClientAddress(String address){
        this.address = address;
    }

    public String getClientAddress(){
        return address;
    }

    public void setClientTelephone(String telephone){
        this.telephone = telephone;
    }

    public String getClientTelephone(){
        return telephone;
    }

    public void setBoreholeLocation(String location){
        this.location = location;
    }

    public String getBoreholeLocation(){
        return location;
    }

    public void setClientCategory(ClientCategory category){
        this.category = category;
    }

    public ClientCategory getClientCategory(){
        return category;
    }

    public String toString(){
        return "ClientId: " + getClientId() + " Name: " + getUsername() + " Email: " + getUserEmail() + " Telephone: " + telephone + " Address: " + address;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if(this == null || getClass() != obj.getClass()) return false;
        Client client = (Client) obj;
        return this.getUsername().equalsIgnoreCase(client.getUsername()) && this.getPassword().equalsIgnoreCase(client.getPassword());
    }
}
