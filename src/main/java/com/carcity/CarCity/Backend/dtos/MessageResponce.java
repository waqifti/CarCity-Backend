package com.carcity.CarCity.Backend.dtos;

public class MessageResponce {
    String message;

    public MessageResponce() {

    }

    public MessageResponce(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
