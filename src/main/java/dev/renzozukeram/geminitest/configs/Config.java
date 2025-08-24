package dev.renzozukeram.geminitest.configs;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.jdbc.PostgresChatMemoryRepositoryDialect;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;

@Configuration
public class Config {

    private Logger log = LoggerFactory.getLogger(Config.class);

    @Value("${spring.ai.vectorstore.simple.store.path}")
    private String vectorStorePath;

    private SimpleVectorStore simpleVectorStoreInstance;

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public ChatMemoryRepository chatMemoryRepository(JdbcTemplate jdbcTemplate) {
        return JdbcChatMemoryRepository.builder()
                .jdbcTemplate(jdbcTemplate)
                .dialect(new PostgresChatMemoryRepositoryDialect())
                .build();
    }

    @Bean
    public ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(chatMemoryRepository)
//                .maxMessages(10)
                .build();
    }

    @Bean
    public VectorStore simpleVectorStore(EmbeddingModel embeddingModel) {

        log.info("Simple vector store");
        File vectorStoreFile = new File(vectorStorePath);

        var builder = SimpleVectorStore.builder(embeddingModel);
        this.simpleVectorStoreInstance = builder.build();

        if (vectorStoreFile.exists()) {
            log.info("Loading vector store from {}", vectorStoreFile.getAbsolutePath());
            try {
                simpleVectorStoreInstance.load(vectorStoreFile);
            } catch (Exception e) {
                log.error("Error loading vector store from: {}", vectorStoreFile.getAbsolutePath(), e);
            }
        } else {
            log.info("Vector store file not found or not file, starting fresh: {}", vectorStoreFile.getAbsolutePath());
        }

        log.info("SimpleVectorStore created successfully.");

        return simpleVectorStoreInstance;
    }

    @PreDestroy
    public void saveVectorStore() {
        if (simpleVectorStoreInstance != null) {
            File vectorStoreFile = new File(vectorStorePath);
            log.info("Saving vector store to {}", vectorStoreFile.getAbsolutePath());

            try {
                simpleVectorStoreInstance.save(vectorStoreFile);
                log.info("Vector store saved successfully.");
            } catch (Exception e) {
                log.error("Error saving vector store to: {}", vectorStoreFile.getAbsolutePath(), e);
            }
        } else {
            log.warn("Vector store wasn't initialized, skipping save.");
        }
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, VectorStore vectorStore) {
        var systemPrompt = "Você é um assistente virtual que irá guiar o usuário.";
        return chatClientBuilder.defaultSystem(systemPrompt)
//                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
//                .defaultAdvisors(VectorStoreChatMemoryAdvisor.builder(vectorStore).build(), QuestionAnswerAdvisor.builder(vectorStore).build())
                .build();
    }
}
