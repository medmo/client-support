package dev.guava.client_support.config;

import dev.guava.client_support.repository.ClientRepository;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentCGVConfig {

    private final DocumentReader documentReader;
    public final VectorStore vectorStore;

    public DocumentCGVConfig(DocumentReader documentReader, VectorStore vectorStore) {
        this.documentReader = documentReader;
        this.vectorStore = vectorStore;
    }

    @Bean
    TokenTextSplitter tokenTextSplitter() {
        return new TokenTextSplitter();
    }

    @Bean
    CommandLineRunner loadData() {
        return args -> {
            vectorStore.accept(new TokenTextSplitter().apply(documentReader.get()));
        };
    }
}
