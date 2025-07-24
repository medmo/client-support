package dev.guava.client_support.actions;

import dev.guava.client_support.dtos.ClientInfosResponse;
import dev.guava.client_support.dtos.ClientRequest;
import dev.guava.client_support.model.Client;
import dev.guava.client_support.service.ClientService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class ClientsActions {

    public final ClientService clientService;

    public ClientsActions(ClientService clientService) {
        this.clientService = clientService;
    }

    @Tool(description = "Ajoute un client à la base de données, le code sera généré automatiquement en incrémentant"+
    "l'id du dernier client et concatené avec 'CLI' et retourne les informations du client ajouté.")
    public Client addClient(ClientRequest request) {
        return clientService.createClient(request);
    }

}
