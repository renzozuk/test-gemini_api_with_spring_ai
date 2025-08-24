package dev.renzozukeram.geminitest.services;

import org.springframework.ai.chat.messages.Message;

import java.util.List;

public interface HistoryService {
    List<Message> getHistory(String conversationId);
    void clearHistory(String conversationId);
}
