package dev.guava.client_support;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssistantController {

    private final ChatClient chatClient;

    public AssistantController(ChatClient.Builder chatClient, ChatMemory chatMemory) {
        this.chatClient = chatClient
            .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
            .build();
    }

    @GetMapping("/assistant")
    public String assistant(@RequestParam String question) {
        return chatClient.prompt()
            .user(question)
            .call()
            .content();
    }
}
