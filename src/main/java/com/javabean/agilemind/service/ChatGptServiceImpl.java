package com.javabean.agilemind.service;

import com.javabean.agilemind.chatgpt.ChatRequest;
import com.javabean.agilemind.chatgpt.ChatResponse;
import com.javabean.agilemind.domain.UserStory;
import com.javabean.agilemind.exceptions.InvalidRequirementsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatGptServiceImpl implements ChatGptService {

    private final RestTemplate restTemplate;

    @Value("${app.openai.model}")
    private String model;

    @Value("${app.openai.url}")
    private String apiUrl;

    @Override
    public List<UserStory> generateUserStories(String requirements) throws InvalidRequirementsException {
        if (requirements.equals(null) || requirements.equals("")) {
            throw new InvalidRequirementsException();
        }

        String promptTemplate = "Generate user stories in a numbered list based on the following requirements: ";
        String reqPrompt = promptTemplate + requirements;
        String response = this.generate(reqPrompt);
        List<String> storyDescriptions = parseStoryDescriptions(response.lines().toList());

        List<UserStory> userStories = new ArrayList<>();
        for (String desc : storyDescriptions) {
            String title = this.getTitleFromDescription(desc);

            UserStory story = new UserStory();
            story.setDescription(desc);
            story.setTitle(title);
            userStories.add(story);
        }
        return userStories;
    }

    protected String generate(String prompt) throws InvalidRequirementsException {
        if (prompt.equals(null) || prompt.equals("")) {
            throw new InvalidRequirementsException();
        }
        // create a request
        ChatRequest request = new ChatRequest(model, prompt);

        // call the API
        ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

        // return the first response
        return response.getChoices().get(0).getMessage().getContent();
    }

    // removes number from start of user story description, ignoring any that doesn't start with a number
    protected List<String> parseStoryDescriptions(List<String> storyDescriptions) {
        List<String> parsedDescriptions = new ArrayList<>();
        for (String desc : storyDescriptions) {
            try {
                Integer.parseInt(desc.substring(0,1));
            } catch (NumberFormatException e) {
                continue;
            }
            parsedDescriptions.add(desc.substring(3));
        }
        return parsedDescriptions;
    }

    // generate a title based on a story description
    protected String getTitleFromDescription(String desc) throws InvalidRequirementsException {
        String titlePrompt = "Generate a short title for this user story enclosed in quotes: " + desc;
        String title = this.generate(titlePrompt);

        // find indexes of the start and end of the title based on the enclosed quotes
        int startIndex = title.indexOf("\"");
        int endIndex = title.indexOf("\"", startIndex + 1);

        return title.substring(startIndex + 1, endIndex);
    }

}
