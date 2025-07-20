package dev.guava.client_support;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssistantController {

    private final ChatClient chatClient;

    public AssistantController(ChatClient.Builder chatClient, PromptChatMemoryAdvisor promptChatMemoryAdvisor) {

        var systemPrompt = """
            Tu es un assistant virtuel pour Guava Télécom, dédié au support client.
            Tu réponds uniquement aux questions liées aux forfaits mobiles, offres Internet, téléphones,
            accessoires, facturation, suivi de commande, SAV ou résiliation.
            Tu refuses poliment toute demande hors de ce périmètre.
            Reste professionnel, clair et orienté service client.
            """;

        this.chatClient = chatClient
            .defaultSystem(systemPrompt)
            .defaultAdvisors(promptChatMemoryAdvisor)
            .build();
    }

    @GetMapping("{user}/assistant")
    public String assistant(@PathVariable("user") String user, @RequestParam String question) {
        return chatClient.prompt()
            .user(question)
            .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, user))
            .call()
            .content();
    }
}
