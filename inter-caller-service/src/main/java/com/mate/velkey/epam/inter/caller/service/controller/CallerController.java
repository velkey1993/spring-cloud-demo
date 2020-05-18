package com.mate.velkey.epam.inter.caller.service.controller;

import com.mate.velkey.epam.inter.caller.service.model.CallmeRequest;
import com.mate.velkey.epam.inter.caller.service.model.CallmeResponse;
import com.mate.velkey.epam.inter.caller.service.model.Conversation;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.mate.velkey.epam.inter.caller.service.InterCallerServiceApplication.ATOMIC_INTEGER;

@RestController
@RequestMapping("/caller")
public class CallerController {

    private final RestTemplate restTemplate;
    private final Resilience4JCircuitBreakerFactory resilience4JCircuitBreakerFactory;

    public CallerController(RestTemplate restTemplate, Resilience4JCircuitBreakerFactory resilience4JCircuitBreakerFactory) {
        this.restTemplate = restTemplate;
        this.resilience4JCircuitBreakerFactory = resilience4JCircuitBreakerFactory;
    }

    @PostMapping("/send/{message}")
    public CallmeResponse send(@PathVariable String message) {
        CallmeRequest callmeRequest = new CallmeRequest(ATOMIC_INTEGER.incrementAndGet(), message);
        return restTemplate.postForObject("http://inter-callme-service/callme/call",
                callmeRequest, CallmeResponse.class);
    }

    @PostMapping("/random-slow-send/{message}")
    public CallmeResponse randomSlowSend(@PathVariable String message) {
        Resilience4JCircuitBreaker resilience4JCircuitBreaker = resilience4JCircuitBreakerFactory.create("random-circuit");
        CallmeRequest callmeRequest = new CallmeRequest(ATOMIC_INTEGER.incrementAndGet(), message);
        return resilience4JCircuitBreaker.run(() ->
                restTemplate.postForObject("http://inter-callme-service/callme/random-slow-call", callmeRequest, CallmeResponse.class));
    }

    @PostMapping("/slow-send/{message}")
    public CallmeResponse slowSend(@PathVariable String message) {
        CallmeRequest callmeRequest = new CallmeRequest(ATOMIC_INTEGER.incrementAndGet(), message);
        return restTemplate.postForObject("http://inter-callme-service/callme/slow-call",
                callmeRequest, CallmeResponse.class);
    }

    @GetMapping("/conversation")
    public List<Conversation> getConversations() {
        return restTemplate
                .exchange(
                        "http://inter-callme-service/callme/conversation",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Conversation>>() {
                        })
                .getBody();
    }

    @GetMapping("/conversation/{requestId}")
    public Conversation getConversationById(@PathVariable int requestId) {
        return restTemplate.getForObject("http://inter-callme-service/callme/conversation/{requestId}", Conversation.class, requestId);
    }
}
