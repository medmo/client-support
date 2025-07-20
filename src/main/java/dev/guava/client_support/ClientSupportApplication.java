package dev.guava.client_support;

import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ClientSupportApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientSupportApplication.class, args);
	}

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory
            .builder()
            .maxMessages(100)
            .build();
    }

}
