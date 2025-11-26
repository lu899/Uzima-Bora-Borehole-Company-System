package model;

import java.util.*;

public abstract class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private Date registrationDate;

    public User(String name, String email){
        this.name = name;
        this.email = email;
    }

    public void setUserId(int id){
        userId = id;
    }

    public int getUserId(){
        return userId;
    }

    public void setUsername(String username){
        name = username;
    }

    public String getUsername(){
        return name;
    }

    public void setUserEmail(String email){
        this.email = email;
    }

    public String getUserEmail(){
        return email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setRegistrationDate(Date date){
        registrationDate = date;
    }

    public Date getRegistrationDate(){
        return registrationDate;
    }

    public String toString(){
        return "userId: " + userId + " Name: " + name + " Email: " + email;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if(this == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return name.equalsIgnoreCase(user.getUsername()) && password.equalsIgnoreCase(user.getPassword());
    }
}
