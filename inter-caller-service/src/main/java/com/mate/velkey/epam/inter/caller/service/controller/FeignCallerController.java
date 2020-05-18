package com.mate.velkey.epam.inter.caller.service.controller;

import com.mate.velkey.epam.inter.caller.service.client.CallmeClient;
import com.mate.velkey.epam.inter.caller.service.model.CallmeRequest;
import com.mate.velkey.epam.inter.caller.service.model.CallmeResponse;
import com.mate.velkey.epam.inter.caller.service.model.Conversation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mate.velkey.epam.inter.caller.service.InterCallerServiceApplication.ATOMIC_INTEGER;

@RestController
@RequestMapping("feign-caller")
public class FeignCallerController {

    private final CallmeClient callmeClient;

    public FeignCallerController(CallmeClient callmeClient) {
        this.callmeClient = callmeClient;
    }

    @PostMapping("/send/{message}")
    public CallmeResponse send(@PathVariable String message) {
        CallmeRequest callmeRequest = new CallmeRequest(ATOMIC_INTEGER.incrementAndGet(), message);
        return callmeClient.send(callmeRequest);
    }

    @PostMapping("/slow-send/{message}")
    public CallmeResponse slowSend(@PathVariable String message) {
        CallmeRequest callmeRequest = new CallmeRequest(ATOMIC_INTEGER.incrementAndGet(), message);
        return callmeClient.slowSend(callmeRequest);
    }

    @GetMapping("/conversation")
    public List<Conversation> getConversations() {
        return callmeClient.getConversations();
    }

    @GetMapping("/conversation/{requestId}")
    public Conversation getConversationById(@PathVariable int requestId) {
        return callmeClient.getConversationById(requestId);
    }
}
