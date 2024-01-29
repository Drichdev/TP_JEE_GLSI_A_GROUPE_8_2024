package com.Ega.EgaBankingBackend.service;

import ch.qos.logback.classic.Logger;
import com.Ega.EgaBankingBackend.Enum.OperationType;
import com.Ega.EgaBankingBackend.dto.*;
import com.Ega.EgaBankingBackend.entity.*;
import com.Ega.EgaBankingBackend.exceptions.ClientNonTrouvrerExceptions;
import com.Ega.EgaBankingBackend.exceptions.CompteNonTrouverExceptions;
import com.Ega.EgaBankingBackend.exceptions.SoldeInsuffisantExeption;
import com.Ega.EgaBankingBackend.mappers.CompteMapperImpl;
import com.Ega.EgaBankingBackend.repository.ClientRepository;
import com.Ega.EgaBankingBackend.repository.CompteRepository;
import com.Ega.EgaBankingBackend.repository.OperationRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class CompteServiceImpl implements CompteService{
    @Autowired
    private  ClientRepository clientRepository;
    @Autowired
    private  CompteRepository compteRepository;
    @Autowired
    private  OperationRepository operationRepository;
    @Autowired
    private CompteMapperImpl dtoMapper;
    public CompteServiceImpl(){}
    Logger log = (Logger) LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        log.info("Sauvegarde d'un nouveau client");
        Client client = dtoMapper.fromCLientDTO(clientDTO);
        Client saveClient = clientRepository.save(client);
        return  dtoMapper.fromClient(saveClient);
    }

    @Override
    public CompteCourantDTO saveCompteCourant(Double initialSolde, double decouvert, Long Client_Id) throws ClientNonTrouvrerExceptions {
        Client client = clientRepository.findById(Client_Id).orElseThrow(null);
        if(client== null)
            throw new ClientNonTrouvrerExceptions("Le client n'a pas été trouvé");
        CompteCourant compteCourant = new CompteCourant();
        compteCourant.setId(String.valueOf(UUID.randomUUID().toString()));
        compteCourant.setDateDeCreation(new Date());
        compteCourant.setSolde(initialSolde);
        compteCourant.setDecouvert(decouvert);
        compteCourant.setClient(client);
        CompteCourant savedCompte = compteRepository.save(compteCourant);
        return dtoMapper.fromCompteCourant(savedCompte);
    }

    @Override
    public CompteEpargneDTO saveCompteEpargne(Double initialSolde, double TauxInteret, Long Client_Id) throws ClientNonTrouvrerExceptions {
        Client client = clientRepository.findById(Client_Id).orElseThrow(null);
        if(client== null)
            throw new ClientNonTrouvrerExceptions("Le client n'a pas été trouvé");
        CompteEpargne compteEpargne = new CompteEpargne();
        compteEpargne.setId(String.valueOf(UUID.randomUUID().toString()));
        compteEpargne.setDateDeCreation(new Date());
        compteEpargne.setSolde(initialSolde);
        compteEpargne.setTauxInteret(TauxInteret);
        compteEpargne.setClient(client);
        CompteEpargne savedCompte = compteRepository.save(compteEpargne);
        return dtoMapper.fromCompteEpargne(savedCompte);
    }


    @Override
    public List<ClientDTO> listClients() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientDTOS = clients.stream()
                .map(client -> dtoMapper.fromClient(client))
                .collect(Collectors.toList());
        return clientDTOS;
    }

    @Override
    public List<CompteDTO> listComptes() {
        List<Compte> comptes  = compteRepository.findAll();
        List<CompteDTO> compteDTOS = comptes.stream().map(compte -> {
            if(compte instanceof CompteEpargne){
                CompteEpargne compteEpargne = (CompteEpargne) compte;
                return dtoMapper.fromCompteEpargne(compteEpargne);
            }else {
                CompteCourant compteCourant = (CompteCourant) compte;
                return dtoMapper.fromCompteCourant(compteCourant);
            }
        }).collect(Collectors.toList());
        return compteDTOS;

    }

    @Override
    public CompteDTO getCompte(String compte_Id) throws CompteNonTrouverExceptions {
        Compte compte = compteRepository.findById(String.valueOf(compte_Id))
                .orElseThrow(()-> new CompteNonTrouverExceptions("Compte non trouvé"));
        if(compte instanceof CompteEpargne){
            CompteEpargne compteEpargne = (CompteEpargne) compte;
            return dtoMapper.fromCompteEpargne(compteEpargne);
        }else {
            CompteCourant compteCourant = (CompteCourant) compte;
            return dtoMapper.fromCompteCourant(compteCourant);
        }
    }

    @Override
    public void debit(String compte_Id, Double montant, String description) throws CompteNonTrouverExceptions, SoldeInsuffisantExeption {
        Compte compte = compteRepository.findById(String.valueOf(compte_Id))
                .orElseThrow(()-> new CompteNonTrouverExceptions("Compte non trouver"));
        if(compte.getSolde()<montant)
            throw new SoldeInsuffisantExeption("Le solde est insuffisant");
        Operation operation = new Operation();
        operation.setType(OperationType.DEBIT);
        operation.setMontant(montant);
        operation.setDescription(description);
        operation.setDateOperation(new Date());
        operation.setCompte(compte);
        operationRepository.save(operation);
        compte.setSolde(compte.getSolde()-montant);
        compteRepository.save(compte);
    }

    @Override
    public void credit(String compte_Id, Double montant, String description) throws CompteNonTrouverExceptions {
        Compte compte = compteRepository.findById(String.valueOf(compte_Id))
                .orElseThrow(()-> new CompteNonTrouverExceptions("Compte non trouver"));
        Operation operation = new Operation();
        operation.setType(OperationType.CREDIT);
        operation.setMontant(montant);
        operation.setDescription(description);
        operation.setDateOperation(new Date());
        operation.setCompte(compte);
        operationRepository.save(operation);
        compte.setSolde(compte.getSolde()+montant);
        compteRepository.save(compte);
    }



    @Override
    public void transfere(String compteIdSource, String compteIdDestination, Double montant) throws CompteNonTrouverExceptions, SoldeInsuffisantExeption {
        debit(compteIdSource,montant,"Tranferer à " + compteIdDestination);
        credit(compteIdDestination,montant,"Tranferer vers " + compteIdSource);

    }

    @Override
    public List<CompteDTO> compteListe(){
        List<Compte> comptes  = compteRepository.findAll();
        List<CompteDTO> compteDTOS = comptes.stream().map(compte -> {
            if(compte instanceof CompteEpargne){
                CompteEpargne compteEpargne = (CompteEpargne) compte;
                return dtoMapper.fromCompteEpargne(compteEpargne);
            }else {
                CompteCourant compteCourant = (CompteCourant) compte;
                return dtoMapper.fromCompteCourant(compteCourant);
            }
        }).collect(Collectors.toList());
        return compteDTOS;
    }

    @Override
    public ClientDTO getClient(Long clientId) throws ClientNonTrouvrerExceptions {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(()-> new ClientNonTrouvrerExceptions("Client non trouvé"));
        return dtoMapper.fromClient(client);
    }

    @Override
    public ClientDTO updateClient(ClientDTO clientDTO) {
        log.info("Sauvegarde d'un nouveau client");
        Client client = dtoMapper.fromCLientDTO(clientDTO);
        Client saveClient = clientRepository.save(client);
        return dtoMapper.fromClient(saveClient);
    }
    @Override
    public  void deleteClient(Long clientId){
        clientRepository.deleteById(clientId);
    }

    @Override
    public List<OperationDTO> CompteHistorique(String compteId){
        List<Operation> operations = operationRepository.findByCompteId(compteId);
        return operations.stream().map( op -> dtoMapper.fromOperation(op )).collect(Collectors.toList());
    }

    @Override
    public CompteHistoriqueDTO getCompteHistorique(String compteId, int page, int taille) throws CompteNonTrouverExceptions {
        Compte compte = compteRepository.findById(compteId).orElse(null);
        if(compte==null) throw new CompteNonTrouverExceptions("Compte non trouvé");
        Page<Operation> operations = operationRepository.findByCompteIdOrderByDateOperationDesc((compteId), PageRequest.of(page,taille));
        CompteHistoriqueDTO compteHistoriqueDTO = new CompteHistoriqueDTO();
        List<OperationDTO> operationDTOS = operations.getContent().stream().map(op -> dtoMapper.fromOperation(op)).collect(Collectors.toList());
        compteHistoriqueDTO.setOperationDTOS(operationDTOS);
        compteHistoriqueDTO.setCompteId(compte.getId());
        compteHistoriqueDTO.setSolde(compte.getSolde());
        compteHistoriqueDTO.setPageCourant(page);
        compteHistoriqueDTO.setTaillePage(taille);
        compteHistoriqueDTO.setPageTotal(operations.getTotalPages());
        return compteHistoriqueDTO;
    }

}


