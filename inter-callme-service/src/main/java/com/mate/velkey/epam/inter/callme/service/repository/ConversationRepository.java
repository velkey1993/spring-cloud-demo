package com.mate.velkey.epam.inter.callme.service.repository;

import com.mate.velkey.epam.inter.callme.service.model.Conversation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ConversationRepository {

    private final List<Conversation> inMemoryRepository = new ArrayList<>();

    public void add(Conversation conversation) {
        inMemoryRepository.add(conversation);
    }

    public List<Conversation> getConversations() {
        return inMemoryRepository;
    }

    public Conversation getConversation(int requestId) {
        return inMemoryRepository.stream()
                .filter(conversation -> conversation.getCallmeRequest().getId() == requestId)
                .findFirst()
                .orElse(null);
    }
}
