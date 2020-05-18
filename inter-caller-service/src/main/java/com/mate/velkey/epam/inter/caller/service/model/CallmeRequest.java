package com.mate.velkey.epam.inter.caller.service.model;

public class CallmeRequest {

    private int id;
    private String message;

    public CallmeRequest() {
    }

    public CallmeRequest(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
