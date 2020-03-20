package com.example.tahreem_fyp;

public class loginClass {

    String userName;
    String userPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public loginClass(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;


    }
}
