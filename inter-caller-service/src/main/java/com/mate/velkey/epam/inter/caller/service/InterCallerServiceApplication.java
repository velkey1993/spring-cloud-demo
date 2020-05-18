package com.mate.velkey.epam.inter.caller.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@EnableFeignClients
public class InterCallerServiceApplication {

	public static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

	public static void main(String[] args) {
		SpringApplication.run(InterCallerServiceApplication.class, args);
	}

}
