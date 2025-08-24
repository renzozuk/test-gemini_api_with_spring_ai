package dev.renzozukeram.geminitest.services;

import dev.renzozukeram.geminitest.dto.PromptRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GeminiChatService implements ChatService {

    private final ChatClient chatClient;

    public GeminiChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String getAnswer(PromptRequest promptRequest) {

        String conversationId = (promptRequest.conversationId() != null)
                ? promptRequest.conversationId()
                : (promptRequest.user() != null)
                ? UUID.nameUUIDFromBytes(promptRequest.user().getBytes()).toString()
                : UUID.randomUUID().toString();

        return chatClient.prompt()
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .user(userMessage -> userMessage.text(promptRequest.prompt()))
                .call().content();
    }
}
