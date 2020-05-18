package com.mate.velkey.epam.inter.callme.service.model;

public class Conversation {

    private final CallmeRequest callmeRequest;
    private final CallmeResponse callmeResponse;

    public Conversation(CallmeRequest callmeRequest, CallmeResponse callmeResponse) {
        this.callmeRequest = callmeRequest;
        this.callmeResponse = callmeResponse;
    }

    public CallmeRequest getCallmeRequest() {
        return callmeRequest;
    }

    public CallmeResponse getCallmeResponse() {
        return callmeResponse;
    }
}
