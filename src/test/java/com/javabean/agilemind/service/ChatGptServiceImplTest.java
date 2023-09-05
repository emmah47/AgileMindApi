package com.javabean.agilemind.service;

import com.javabean.agilemind.chatgpt.ChatRequest;
import com.javabean.agilemind.chatgpt.ChatResponse;
import com.javabean.agilemind.chatgpt.Message;
import com.javabean.agilemind.exceptions.InvalidRequirementsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class ChatGptServiceImplTest {
    RestTemplate mockTemplate;
    ChatGptServiceImpl gpt;
    @BeforeEach
    void setup() {
        mockTemplate = mock(RestTemplate.class);
        gpt = spy(new ChatGptServiceImpl(mockTemplate));
    }

    @Test
    void generateUserStories() {
    }

    @Test
    void parseStoryDescriptionsEmpty() {
        List<String> emptyDesc = new ArrayList<>();
        assertEquals(0 ,gpt.parseStoryDescriptions(emptyDesc).size());
    }

    @Test
    void parseStoryDescriptionsOnlyInvalidEntries() {
        List<String> desc = new ArrayList<>();
        desc.add("test string");
        desc.add("test string2");
        assertEquals(0 ,gpt.parseStoryDescriptions(desc).size());
    }

    @Test
    void parseStoryDescriptionsSingleNumberedEntry() {
        List<String> desc = new ArrayList<>();
        desc.add("1. user story 1");
        assertEquals(1 ,gpt.parseStoryDescriptions(desc).size());
        assertEquals("user story 1",gpt.parseStoryDescriptions(desc).get(0));
    }

    @Test void parseStoryDescriptionMultipleEntriesIncludingInvalid() {
        List<String> desc = new ArrayList<>();
        desc.add("Test string");
        desc.add("1. user story 1");
        desc.add("Test string");
        desc.add("2. user story 2");
        desc.add("Test string");

        assertEquals(2 ,gpt.parseStoryDescriptions(desc).size());
        assertEquals("user story 1",gpt.parseStoryDescriptions(desc).get(0));
        assertEquals("user story 2",gpt.parseStoryDescriptions(desc).get(1));


    }

    @Test
    void getTitleFromDescriptionPadding() throws InvalidRequirementsException {
        String desc = "description 1";
        doReturn("Title: \"title\"").when(gpt).generate(anyString());
        String title = gpt.getTitleFromDescription(desc);
        assertEquals("title", title);

    }

    @Test
    void getTitleFromDescriptionNoPadding() throws InvalidRequirementsException {
        String desc = "description 2";
        doReturn("\"title\"").when(gpt).generate(anyString());
        String title = gpt.getTitleFromDescription(desc);
        assertEquals("title", title);

    }
}