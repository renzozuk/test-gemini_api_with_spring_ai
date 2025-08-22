package dev.renzozukeram.geminitest.controllers;

import dev.renzozukeram.geminitest.services.ChatService;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/models")
    public ResponseEntity<Map> getModels() {
        return ResponseEntity.ok(chatService.getModels());
    }

    @PostMapping("/prompt")
    public ResponseEntity<String> prompt(@RequestParam String question) {
        return ResponseEntity.ok(chatService.getAnswer(question));
    }

    @GetMapping("/history")
    public ResponseEntity<List<Message>> getHistory(@RequestParam String conversationId) {
        return ResponseEntity.ok(chatService.getHistory(conversationId));
    }

    @DeleteMapping("/history")
    public ResponseEntity<Void> deleteHistory(@RequestParam String conversationId) {
        chatService.clearHistory(conversationId);
        return ResponseEntity.noContent().build();
    }
}
