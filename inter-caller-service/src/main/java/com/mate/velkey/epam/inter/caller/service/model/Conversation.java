package com.mate.velkey.epam.inter.caller.service.model;

public class Conversation {

    private CallmeRequest callmeRequest;
    private CallmeResponse callmeResponse;

    public CallmeRequest getCallmeRequest() {
        return callmeRequest;
    }

    public void setCallmeRequest(CallmeRequest callmeRequest) {
        this.callmeRequest = callmeRequest;
    }

    public CallmeResponse getCallmeResponse() {
        return callmeResponse;
    }

    public void setCallmeResponse(CallmeResponse callmeResponse) {
        this.callmeResponse = callmeResponse;
    }
}
