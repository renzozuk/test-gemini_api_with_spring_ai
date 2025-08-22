package dev.renzozukeram.geminitest.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    @Value("${spring.ai.openai.api-key}")
    private String key;

    private final ChatClient chatClient;
    private final RestClient restClient;
    private final ChatMemory chatMemory;

    public ChatService(ChatClient chatClient, RestClient.Builder restClientBuilder, ChatMemory chatMemory) {
        this.chatClient = chatClient;
        this.restClient = restClientBuilder.baseUrl("https://generativelanguage.googleapis.com").build();
        this.chatMemory = chatMemory;
    }

    public Map<String, Object> getModels() {

         return restClient.get()
                .uri("/v1beta/openai/models")
                .header("Authorization", "Bearer " + key)
                .retrieve()
                .toEntity(Map.class).getBody();
    }

    public String getAnswer(String question) {
        return chatClient.prompt().user(question).call().content();
    }

    public List<Message> getHistory(String conversationId) {
        return chatMemory.get(conversationId);
    }

    public void clearHistory(String conversationId) {
        chatMemory.clear(conversationId);
    }
}
