package com.mate.velkey.epam.inter.caller.service.client;

import com.mate.velkey.epam.inter.caller.service.model.CallmeRequest;
import com.mate.velkey.epam.inter.caller.service.model.CallmeResponse;
import com.mate.velkey.epam.inter.caller.service.model.Conversation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "inter-callme-service", path = "/callme")
public interface CallmeClient {

    @PostMapping("/call")
    CallmeResponse send(@RequestBody CallmeRequest callmeRequest);

    @PostMapping("/slow-call")
    CallmeResponse slowSend(@RequestBody CallmeRequest callmeRequest);

    @GetMapping("/conversation")
    List<Conversation> getConversations();

    @GetMapping("/conversation/{requestId}")
    Conversation getConversationById(@PathVariable int requestId);
}
