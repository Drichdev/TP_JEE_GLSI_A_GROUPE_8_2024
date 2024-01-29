package com.Ega.EgaBankingBackend.security;

import com.Ega.EgaBankingBackend.entity.Client;
import com.Ega.EgaBankingBackend.entity.Log;
import com.Ega.EgaBankingBackend.mappers.CompteMapperImpl;
import com.Ega.EgaBankingBackend.repository.ClientRepository;
import com.Ega.EgaBankingBackend.repository.LogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LoginSuccessHandler {
    private LogRepository logRepository;
    private ClientRepository clientRepository;
    private CompteMapperImpl compteMapper;

    public LoginSuccessHandler(LogRepository logRepository, ClientRepository clientRepository, CompteMapperImpl compteMapper) {
        this.logRepository = logRepository;
        this.clientRepository = clientRepository;
        this.compteMapper = compteMapper;

    }

    public void onAuthenticationSuccess(String username, String remoteAddr, String remoteHost) throws IOException, ServletException {
        Client user = this.clientRepository.findByLogin(username).orElse(null);
        ObjectMapper mapper = new ObjectMapper();

        Log log = new Log();
        log.setAction("Connexion");
        // TODO: enregistre les logs
    }
}
