package dev.guava.client_support.config;

import dev.guava.client_support.model.Client;
import dev.guava.client_support.repository.ClientRepository;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class clientSupportConfig {

    public final ClientReader clientReader;
    public final VectorStore vectorStore;

    public clientSupportConfig(ClientReader clientReader, VectorStore vectorStore) {
        this.clientReader = clientReader;
        this.vectorStore = vectorStore;
    }

    @Bean
    TokenTextSplitter tokenTextSplitter() {
        return new TokenTextSplitter();
    }

    @Bean
    CommandLineRunner loadData(ClientRepository clientRepository) {
        return args -> {
            // petit hack a enlever pour ne pas recharger les données à chaque démarrage
            if (clientRepository.count() == 5) {
                return;
            }
            vectorStore.accept(new TokenTextSplitter().apply(clientReader.get()));
        };
    }

}
