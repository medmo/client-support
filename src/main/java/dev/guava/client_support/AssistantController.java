package dev.guava.client_support;

import dev.guava.client_support.dtos.ClientInfosResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssistantController {

    private final ChatClient chatClient;
    private final PgVectorStore vectorStore;

    public AssistantController(ChatClient.Builder chatClient, PromptChatMemoryAdvisor promptChatMemoryAdvisor, PgVectorStore vectorStore) {

        var systemPrompt = """
            Tu es un assistant virtuel pour Guava Télécom, tu aides le support client à obtenir des informations sur les clients.
            Tu peux répondre aux questions sur les clients en utilisant les informations stockées dans la base de données.
            Tu peux fournir les informations personnelles suivantes :
            - Nom
            - numéro de téléphone
            - adresse e-mail
            - adresse postale
            """;

        this.chatClient = chatClient
            .defaultSystem(systemPrompt)
            .defaultAdvisors(promptChatMemoryAdvisor, new QuestionAnswerAdvisor(vectorStore))
            .build();
        this.vectorStore = vectorStore;
    }

    @GetMapping("{user}/assistant")
    public String assistant(@PathVariable("user") String user, @RequestParam String question) {
        return chatClient.prompt()
            .user(question)
            .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, user))
            .call()
            .content();
    }

    @GetMapping("{user}/assistant-so")
    public ClientInfosResponse assistantStructuredOutput(@PathVariable("user") String user, @RequestParam String question) {
        return chatClient.prompt()
            .user(question)
            .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, user))
            .call()
            .entity(ClientInfosResponse.class);
    }
}
