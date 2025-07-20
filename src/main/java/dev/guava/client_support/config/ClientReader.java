package dev.guava.client_support.config;

import dev.guava.client_support.service.ClientService;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
class ClientReader implements DocumentReader {

    private final ClientService clientService;

    ClientReader(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public List<Document> get() {
        return clientService.findAll().stream()
            .map(client ->
                new Document("code: %s, name: %s, email: %s, phone: %s, adress: %s".formatted(
                    client.getCode(), client.getName(), client.getEmail(), client.getPhoneNumber(), client.getAdress())))
            .collect(Collectors.toList());

    }
}
