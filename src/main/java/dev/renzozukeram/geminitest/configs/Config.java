package dev.renzozukeram.geminitest.configs;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        var systemPrompt = "Você é um assistente virtual que irá guiar o usuário.";
        return chatClientBuilder.defaultSystem(systemPrompt).build();
    }
}
