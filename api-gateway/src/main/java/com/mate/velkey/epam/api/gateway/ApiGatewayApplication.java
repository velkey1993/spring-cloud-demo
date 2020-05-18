package com.mate.velkey.epam.api.gateway;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public KeyResolver keyResolver() {
        return exchange -> Mono.just("1");
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> {
            factory.configureDefault(id ->
                    new Resilience4JConfigBuilder(id)
                            .timeLimiterConfig(
                                    TimeLimiterConfig.custom()
                                            .timeoutDuration(Duration.ofMillis(500))
                                            .build())
                            .circuitBreakerConfig(
                                    CircuitBreakerConfig.custom()
                                            .slidingWindowSize(10)
                                            .failureRateThreshold(80)
                                            .build())
                            .build());
        };
    }
}
