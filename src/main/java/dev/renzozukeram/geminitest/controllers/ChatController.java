package dev.renzozukeram.geminitest.controllers;

import dev.renzozukeram.geminitest.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/models")
    public ResponseEntity<Map> getModels() {
        return ResponseEntity.ok(chatService.getModels());
    }

    @GetMapping("/prompt")
    public String prompt(@RequestParam String question) {
        return chatService.getAnswer(question);
    }
}
