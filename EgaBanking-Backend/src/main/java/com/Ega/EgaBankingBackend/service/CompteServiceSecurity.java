package com.Ega.EgaBankingBackend.service;

import com.Ega.EgaBankingBackend.entity.Client;
import com.Ega.EgaBankingBackend.repository.ClientRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CompteServiceSecurity implements UserDetailsService {
    private ClientRepository clientRepository;


    public CompteServiceSecurity(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Client client = clientRepository.findByLoginAndActif(username,Boolean.TRUE);
        // System.err.println(utilisateur);
        if (client==null){
            throw new UsernameNotFoundException("L'utilisateur n'existe pas");
        }


        User user = new User(client.getLogin(), client.getPassword(), new ArrayList<>());

        return user;
    }
}
