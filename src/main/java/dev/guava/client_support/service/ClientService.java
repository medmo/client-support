package dev.guava.client_support.service;

import dev.guava.client_support.dtos.ClientRequest;
import dev.guava.client_support.model.Client;
import dev.guava.client_support.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return (List<Client>) clientRepository.findAll();
    }

    public Client createClient(ClientRequest request) {
        Client client = new Client();
        client.setName(request.name());
        client.setPhoneNumber(request.phoneNumber());
        client.setEmail(request.email());
        client.setAdress(request.address());
        client.setCode(generateCode());
        client =  clientRepository.save(client);
        return client;
    }

    private String generateCode() {
        Long lastId = clientRepository.findLastId();
        return "CLI" + (lastId != null ? (lastId + 1) : 1);
    }
}
