package com.Ega.EgaBankingBackend.controller;

import com.Ega.EgaBankingBackend.dto.ClientDTO;
import com.Ega.EgaBankingBackend.exceptions.ClientNonTrouvrerExceptions;
import com.Ega.EgaBankingBackend.service.CompteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class ClientRestController {
    private CompteService compteService;

    @GetMapping("/clients")
    public List<ClientDTO> clients(){
        return compteService.listClients();
    }
    @GetMapping("/clients/{Id}")
    public ClientDTO getClientDTO(@PathVariable(name ="Id") Long clientId) throws ClientNonTrouvrerExceptions {
        return compteService.getClient(clientId);
    }

    @PostMapping("/clients")
    public ClientDTO saveClient(@RequestBody ClientDTO clientDTO){
        return compteService.saveClient(clientDTO);
    }

    @PutMapping("/clients/{clientId}")
    public ClientDTO updateClient(@PathVariable Long clientId, @RequestBody ClientDTO clientDTO){
        clientDTO.setId(clientId);
        return compteService.updateClient(clientDTO);

    }
    @DeleteMapping("/client/{Id}")
    public void deleteClient(@PathVariable Long Id){
        compteService.deleteClient(Id);
    }



}

