package com.mate.velkey.epam.inter.caller.service.configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
public class CallerConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
//                .setReadTimeout(Duration.ofMillis(200))
//                .setConnectTimeout(Duration.ofMillis(200))
                .build();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 200)
                .doOnConnected(connection -> connection.addHandler(new ReadTimeoutHandler(200, TimeUnit.MILLISECONDS)));
        ReactorClientHttpConnector reactorClientHttpConnector = new ReactorClientHttpConnector(HttpClient.from(tcpClient));
        return WebClient.builder().clientConnector(reactorClientHttpConnector);
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
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
