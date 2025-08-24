package dev.renzozukeram.geminitest.services;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeminiHistoryService implements HistoryService {

    private final ChatMemory chatMemory;

    public GeminiHistoryService(ChatMemory chatMemory) {
        this.chatMemory = chatMemory;
    }

    @Override
    public List<Message> getHistory(String conversationId) {
        return chatMemory.get(conversationId);
    }

    @Override
    public void clearHistory(String conversationId) {
        chatMemory.clear(conversationId);
    }
}
