package com.jake.openai.chatgpt.controller;

import com.jake.openai.chatgpt.dto.ChatBotRequest;
import com.jake.openai.chatgpt.dto.ChatBotResponse;
import com.jake.openai.chatgpt.dto.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ChatBotController {

    @Value("${openai.chatgpt.model}")
    private String model;

    @Value("${openai.chatgpt.max-completions}")
    private int maxCompletions;

    @Value("${openai.chatgpt.temperature}")
    private double temperature;

    @Value("${openai.chatgpt.max_tokens}")
    private int maxTokens;

    @Value("${openai.chatgpt.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public ChatBotController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/chat")
    public ChatBotResponse chat(@RequestParam("prompt") String prompt) {
        ChatBotRequest request = new ChatBotRequest(
                model,
                List.of(new Message("user", prompt)),
                maxCompletions,
                temperature);
//                maxTokens);

        return restTemplate.postForObject(apiUrl, request, ChatBotResponse.class);
    }
}
