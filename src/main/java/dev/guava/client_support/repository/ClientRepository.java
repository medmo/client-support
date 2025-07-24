package dev.guava.client_support.repository;

import dev.guava.client_support.model.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query("SELECT MAX(c.id) FROM Client c")
    Long findLastId();
}
