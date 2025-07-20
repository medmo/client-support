package dev.guava.client_support;

import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class ClientSupportApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientSupportApplication.class, args);
	}

    @Bean
    PromptChatMemoryAdvisor promptChatMemoryAdvisor(DataSource dataSource) {
        var jdbc = JdbcChatMemoryRepository
            .builder()
            .dataSource(dataSource)
            .build();

        var chatMessageWindow = MessageWindowChatMemory
            .builder()
            .chatMemoryRepository(jdbc)
            .build();

        return PromptChatMemoryAdvisor
            .builder(chatMessageWindow)
            .build();
    }

}
