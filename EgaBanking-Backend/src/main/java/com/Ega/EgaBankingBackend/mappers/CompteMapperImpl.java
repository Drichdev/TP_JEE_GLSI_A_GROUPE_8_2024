package com.Ega.EgaBankingBackend.mappers;

import com.Ega.EgaBankingBackend.dto.ClientDTO;
import com.Ega.EgaBankingBackend.dto.CompteCourantDTO;
import com.Ega.EgaBankingBackend.dto.CompteEpargneDTO;
import com.Ega.EgaBankingBackend.dto.OperationDTO;
import com.Ega.EgaBankingBackend.entity.Client;
import com.Ega.EgaBankingBackend.entity.CompteCourant;
import com.Ega.EgaBankingBackend.entity.CompteEpargne;
import com.Ega.EgaBankingBackend.entity.Operation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CompteMapperImpl {
    public ClientDTO fromClient(Client client){
        ClientDTO clientDTO=new ClientDTO();
        BeanUtils.copyProperties(client,clientDTO);
        return  clientDTO;
    }

    public Client fromCLientDTO(ClientDTO clientDTO){
        Client client = new Client();
        BeanUtils.copyProperties(clientDTO,client);
        return  client;
    }


    public CompteEpargneDTO fromCompteEpargne(CompteEpargne compteEpargne){
        CompteEpargneDTO compteEpargneDTO = new CompteEpargneDTO();
        BeanUtils.copyProperties(compteEpargne,compteEpargneDTO);
        compteEpargneDTO.setClientDTO(fromClient(compteEpargne.getClient()));
        compteEpargneDTO.setType(compteEpargne.getClass().getSimpleName());
        return compteEpargneDTO;
    }

    public CompteEpargne fromCompteEpargneDTO(CompteEpargneDTO compteEpargneDTO){
        CompteEpargne compteEpargne = new CompteEpargne();
        BeanUtils.copyProperties(compteEpargneDTO,compteEpargne);
        compteEpargne.setClient(fromCLientDTO(compteEpargneDTO.getClientDTO()));
        return compteEpargne;
    }


    public CompteCourantDTO fromCompteCourant(CompteCourant compteCourant){
        CompteCourantDTO compteCourantDTO =new CompteCourantDTO();
        BeanUtils.copyProperties(compteCourant,compteCourantDTO);
        CompteCourantDTO.setClientDTO(fromClient(compteCourant.getClient()));
        compteCourantDTO.setType(compteCourant.getClass().getSimpleName());
        return compteCourantDTO;
    }


    public CompteCourant fromCompteCourantDTO(CompteCourantDTO compteCourantDTO){
        CompteCourant compteCourant = new CompteCourant();
        BeanUtils.copyProperties(compteCourantDTO,compteCourant);
        compteCourant.setClient(fromCLientDTO(CompteCourantDTO.getClientDTO()));
        return compteCourant;
    }


    public OperationDTO fromOperation(Operation operation){
        OperationDTO operationDTO = new OperationDTO();
        BeanUtils.copyProperties(operation,operationDTO);
        return operationDTO;
    }

}
