package dev.renzozukeram.geminitest.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class GeminiModelsProviderService implements ModelsProviderService {

    @Value("${spring.ai.openai.api-key}")
    private String key;

    private final RestClient restClient;

    public GeminiModelsProviderService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl("https://generativelanguage.googleapis.com").build();
    }

    @Override
    public Map<String, Object> getModels() {

        return restClient.get()
                .uri("/v1beta/openai/models")
                .header("Authorization", "Bearer " + key)
                .retrieve()
                .toEntity(Map.class).getBody();
    }
}
