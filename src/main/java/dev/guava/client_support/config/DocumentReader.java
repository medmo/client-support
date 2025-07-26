package dev.guava.client_support.config;

import dev.guava.client_support.service.ClientService;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
class DocumentReader implements org.springframework.ai.document.DocumentReader {

    private final Resource resource;

    DocumentReader(@Value("classpath:CGV_Guava_Telecom.txt") Resource resource) {
        this.resource = resource;
    }

    @Override
    public List<Document> get() {
        TextReader textReader = new TextReader(this.resource);
        textReader.getCustomMetadata().put("filename", "CGV_Guava_Telecom.txt");

        return textReader.read();

    }
}
