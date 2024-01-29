package com.Ega.EgaBankingBackend.repository;

import com.Ega.EgaBankingBackend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByLoginAndActif(String username, Boolean aTrue);
    Optional<Client> findByLogin(String login);
}

