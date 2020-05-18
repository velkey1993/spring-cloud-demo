package com.mate.velkey.epam.inter.caller.service.controller;

import com.mate.velkey.epam.inter.caller.service.model.CallmeRequest;
import com.mate.velkey.epam.inter.caller.service.model.CallmeResponse;
import com.mate.velkey.epam.inter.caller.service.model.Conversation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.mate.velkey.epam.inter.caller.service.InterCallerServiceApplication.ATOMIC_INTEGER;

@RestController
@RequestMapping("/reactive-caller")
public class ReactiveCallerController {

    private final WebClient.Builder webClientBuilder;

    public ReactiveCallerController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping("/send/{message}")
    public Mono<CallmeResponse> send(@PathVariable String message) {
        CallmeRequest callmeRequest = new CallmeRequest(ATOMIC_INTEGER.incrementAndGet(), message);
        return webClientBuilder.build()
                .post()
                .uri("http://inter-callme-service/callme/call")
                .bodyValue(callmeRequest)
                .retrieve()
                .bodyToMono(CallmeResponse.class);
    }

    @PostMapping("/slow-send/{message}")
    public Mono<CallmeResponse> slowSend(@PathVariable String message) {
        CallmeRequest callmeRequest = new CallmeRequest(ATOMIC_INTEGER.incrementAndGet(), message);
        return webClientBuilder.build()
                .post()
                .uri("http://inter-callme-service/callme/slow-call")
                .bodyValue(callmeRequest)
                .retrieve()
                .bodyToMono(CallmeResponse.class);
    }

    @GetMapping("/conversation")
    public Flux<Conversation> getConversations() {
        return webClientBuilder.build()
                .get()
                .uri("http://inter-callme-service/callme/conversation")
                .retrieve()
                .bodyToFlux(Conversation.class);
    }

    @GetMapping("/conversation/{requestId}")
    public Mono<Conversation> getConversationById(@PathVariable int requestId) {
        return webClientBuilder.build()
                .get()
                .uri("http://inter-callme-service/callme/conversation/{requestId}", requestId)
                .retrieve()
                .bodyToMono(Conversation.class);
    }
}
