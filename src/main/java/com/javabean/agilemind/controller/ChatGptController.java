package com.javabean.agilemind.controller;

import com.javabean.agilemind.chatgpt.ChatRequest;
import com.javabean.agilemind.chatgpt.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


// THIS CLASS IS FOR DEBUGGING PURPOSES

@RestController
@RequestMapping("chat")
@RequiredArgsConstructor
public class ChatGptController {

//    @Qualifier("openaiRestTemplate")
//    @Autowired
    private final RestTemplate restTemplate;

    @Value("${app.openai.model}")
    private String model;

    @Value("${app.openai.url}")
    private String apiUrl;

    @GetMapping("")
    public String chat(//@RequestParam String prompt
    ) {
        // create a request
        ChatRequest request = new ChatRequest(model, "come up with 3 user stories for a todo list in point form");

        // call the API
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        String result = response.getChoices().get(0).getMessage().getContent();

        // return the first response
        return result;
    }
}
