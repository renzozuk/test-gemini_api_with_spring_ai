package dev.renzozukeram.geminitest.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class ChatService {

    @Value("${spring.ai.openai.api-key}")
    private String key;

    private final ChatClient chatClient;
    private final RestClient restClient;

    public ChatService(ChatClient chatClient, RestClient.Builder restClientBuilder) {
        this.chatClient = chatClient;
        this.restClient = restClientBuilder.baseUrl("https://generativelanguage.googleapis.com").build();
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
}
