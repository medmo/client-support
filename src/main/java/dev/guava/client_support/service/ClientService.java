package dev.guava.client_support.service;

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
}
