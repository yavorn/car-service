package com.telerikacademy.carservice.controllers.handlers;

public class ErrorHandler {

    private int status;

    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("Error %d : %s", this.getStatus(), this.getMessage());
    }
}
