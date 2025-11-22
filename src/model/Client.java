package model;

import java.util.*;

public class Client {
    private int clientId;
    private String name;
    private String address;
    private String telephone;
    private String email;
    private String password;
    private String location;
    private ClientCategory category;
    private Date registrationDate;

    public Client(String name, String address, String telephone, String email, String location, ClientCategory category){
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.location = location;
        this.category = category;
    }

    public void setClientId(int id){
        clientId = id;
    }

    public int getClientId(){
        return clientId;
    }

    public void setClientName(String username){
        name = username;
    }

    public String getClientName(){
        return name;
    }

    public void setClientAddress(String address){
        this.address = address;
    }

    public String getClientAddress(){
        return address;
    }

    public void setClientEmail(String email){
        this.email = email;
    }

    public String getClientEmail(){
        return email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
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

    public void setRegistrationDate(Date date){
        registrationDate = date;
    }

    public Date getRegistrationDate(){
        return registrationDate;
    }

    public String toString(){
        return "ClientId: " + clientId + " Name: " + name + " Email: " + email + " Telephone: " + telephone + " Address: " + address;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if(this == null || getClass() != obj.getClass()) return false;
        Client client = (Client) obj;
        return name.equalsIgnoreCase(client.getClientName()) && password.equalsIgnoreCase(client.getPassword());
    }
}
