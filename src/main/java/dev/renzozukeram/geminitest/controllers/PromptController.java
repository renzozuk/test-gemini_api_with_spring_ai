package dev.renzozukeram.geminitest.controllers;

import dev.renzozukeram.geminitest.dto.PromptRequest;
import dev.renzozukeram.geminitest.services.ChatService;
import dev.renzozukeram.geminitest.services.HistoryService;
import dev.renzozukeram.geminitest.services.ModelsProviderService;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class PromptController {

    private final ChatService chatService;
    private final HistoryService historyService;
    private final ModelsProviderService modelsProviderService;

    @Autowired
    public PromptController(ChatService chatService, HistoryService historyService, ModelsProviderService modelsProviderService) {
        this.chatService = chatService;
        this.historyService = historyService;
        this.modelsProviderService = modelsProviderService;
    }

    @PostMapping("/prompt")
    public ResponseEntity<String> prompt(@RequestBody PromptRequest promptRequest) {
        return ResponseEntity.ok(chatService.getAnswer(promptRequest));
    }

    @GetMapping("/history")
    public ResponseEntity<List<Message>> getHistory(@RequestParam String conversationId) {
        return ResponseEntity.ok(historyService.getHistory(conversationId));
    }

    @DeleteMapping("/history")
    public ResponseEntity<Void> deleteHistory(@RequestParam String conversationId) {
        historyService.clearHistory(conversationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/models")
    public ResponseEntity<Map> getModels() {
        return ResponseEntity.ok(modelsProviderService.getModels());
    }
}
