package com.mate.velkey.epam.api.gateway.controller;

import com.mate.velkey.epam.api.gateway.model.GatewayResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    @PostMapping("/test")
    public Mono<GatewayResponse> fallback() {
        return Mono.just(new GatewayResponse(ATOMIC_INTEGER.incrementAndGet(), "I'm fallback."));
    }
}
