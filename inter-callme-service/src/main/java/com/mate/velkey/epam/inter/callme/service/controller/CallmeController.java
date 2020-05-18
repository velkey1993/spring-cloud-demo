package com.mate.velkey.epam.inter.callme.service.controller;

import com.mate.velkey.epam.inter.callme.service.model.CallmeRequest;
import com.mate.velkey.epam.inter.callme.service.model.CallmeResponse;
import com.mate.velkey.epam.inter.callme.service.model.Conversation;
import com.mate.velkey.epam.inter.callme.service.repository.ConversationRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/callme")
public class CallmeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallmeController.class);

    private final ConversationRepository conversationRepository;
    private final Long delay;

    public CallmeController(ConversationRepository conversationRepository, @Value("${app.delay}") Long delay) {
        this.conversationRepository = conversationRepository;
        this.delay = delay;
    }

    @PostMapping("/call")
    public CallmeResponse call(@RequestBody CallmeRequest callmeRequest) {
        CallmeResponse callmeResponse = new CallmeResponse(callmeRequest.getId(), StringUtils.reverse(callmeRequest.getMessage()));
        conversationRepository.add(new Conversation(callmeRequest, callmeResponse));
        return callmeResponse;
    }

    @PostMapping("/random-slow-call")
    public CallmeResponse randomSlowCall(@RequestBody CallmeRequest callmeRequest) throws InterruptedException {
        long randomDelay = 0;
        if (delay != 0) {
            randomDelay = ThreadLocalRandom.current().nextLong(delay);
            Thread.sleep(randomDelay);
        }
        LOGGER.info("Request: message -> {}, delay -> {}", callmeRequest.getMessage(), randomDelay);
        CallmeResponse callmeResponse = new CallmeResponse(callmeRequest.getId(), StringUtils.reverse(callmeRequest.getMessage()));
        conversationRepository.add(new Conversation(callmeRequest, callmeResponse));
        return callmeResponse;
    }

    @PostMapping("/slow-call")
    public CallmeResponse slowCall(@RequestBody CallmeRequest callmeRequest) throws InterruptedException {
        Thread.sleep(1000);
        CallmeResponse callmeResponse = new CallmeResponse(callmeRequest.getId(), StringUtils.reverse(callmeRequest.getMessage()));
        conversationRepository.add(new Conversation(callmeRequest, callmeResponse));
        return callmeResponse;
    }

    @GetMapping("/conversation")
    public List<Conversation> getConversations() {
        return conversationRepository.getConversations();
    }

    @GetMapping("/conversation/{requestId}")
    public Conversation getConversationById(@PathVariable int requestId) {
        return conversationRepository.getConversation(requestId);
    }
}
