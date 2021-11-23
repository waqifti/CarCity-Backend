package com.carcity.CarCity.Backend.dtos;

public class LoginResponce {
    private String sessiontoken;

    public LoginResponce() {

    }

    public LoginResponce(String sessiontoken) {
        this.sessiontoken = sessiontoken;
    }

    public String getSessiontoken() {
        return sessiontoken;
    }

    public void setSessiontoken(String sessiontoken) {
        this.sessiontoken = sessiontoken;
    }
}
