package dev.renzozukeram.geminitest.services;

import dev.renzozukeram.geminitest.dto.PromptRequest;

public interface ChatService {
    String getAnswer(PromptRequest promptRequest);
}
