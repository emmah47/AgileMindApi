package com.javabean.agilemind.chatgpt;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChatRequest {
    private String model;
    private List<Message> messages;
    private int n;
    private double temperature;

    public ChatRequest(String model, String prompt) {
        this.model = model;
        this.n = 1;
        this.messages = new ArrayList<>();
        this.messages.add(Message.builder().role("user").content(prompt).build());
    }
}
