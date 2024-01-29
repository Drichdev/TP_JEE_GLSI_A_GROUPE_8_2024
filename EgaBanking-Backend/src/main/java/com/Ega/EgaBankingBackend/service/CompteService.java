package com.Ega.EgaBankingBackend.service;

import com.Ega.EgaBankingBackend.dto.*;
import com.Ega.EgaBankingBackend.exceptions.ClientNonTrouvrerExceptions;
import com.Ega.EgaBankingBackend.exceptions.CompteNonTrouverExceptions;
import com.Ega.EgaBankingBackend.exceptions.SoldeInsuffisantExeption;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompteService {
    ClientDTO saveClient(ClientDTO clientDTO);
    CompteCourantDTO saveCompteCourant(Double initialSolde, double decouvert, Long Client_Id) throws ClientNonTrouvrerExceptions;
    CompteEpargneDTO saveCompteEpargne(Double initialSolde, double TauxInteret, Long Client_Id) throws ClientNonTrouvrerExceptions;
    List<ClientDTO> listClients();
    List<CompteDTO> listComptes();

    CompteDTO getCompte(String compte_Id) throws CompteNonTrouverExceptions;

    void debit(String compte_Id, Double montant, String description) throws CompteNonTrouverExceptions, SoldeInsuffisantExeption;

    void credit(String compte_Id, Double montant, String description) throws CompteNonTrouverExceptions;

    void transfere(String compteIdSource, String compteIdDestination, Double montant) throws CompteNonTrouverExceptions, SoldeInsuffisantExeption;

    List<CompteDTO> compteListe();

    ClientDTO getClient(Long clientId) throws ClientNonTrouvrerExceptions;

    ClientDTO updateClient(ClientDTO clientDTO);

    void deleteClient(Long Id);
    List<OperationDTO> CompteHistorique(String compteId);

    CompteHistoriqueDTO getCompteHistorique(String compteId, int page, int taille) throws CompteNonTrouverExceptions;
}
