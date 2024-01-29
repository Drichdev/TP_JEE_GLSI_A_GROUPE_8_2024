package com.Ega.EgaBankingBackend.controller;

import com.Ega.EgaBankingBackend.dto.*;
import com.Ega.EgaBankingBackend.exceptions.CompteNonTrouverExceptions;
import com.Ega.EgaBankingBackend.exceptions.SoldeInsuffisantExeption;
import com.Ega.EgaBankingBackend.service.CompteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CompteRestAPI {
    private CompteService compteService;
    public CompteRestAPI(CompteService compteService) {
        this.compteService = compteService;
    }
    @GetMapping("/comptes/{CompteId}")
    public CompteDTO getCompte(@PathVariable String CompteId) throws CompteNonTrouverExceptions{
        return compteService.getCompte(CompteId);
    }
    @GetMapping("/comptes")
    public List<CompteDTO> ListComptes(){
        return compteService.listComptes();
    }
    @GetMapping("/comptes/{compteId}/operations")
    public List<OperationDTO> getHistorique(@PathVariable String compteId){
        return  compteService.CompteHistorique(compteId);
    }
    @GetMapping("/comptes/{compteId}/pageOperations")
    public CompteHistoriqueDTO getCompteHistorique(
            @PathVariable String compteId,
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "taille",defaultValue = "5") int taille

    ) throws CompteNonTrouverExceptions {
        return  compteService.getCompteHistorique(compteId,page,taille);
    }

    @PostMapping("/comptes/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws CompteNonTrouverExceptions, SoldeInsuffisantExeption {
        this.compteService.debit(debitDTO.getCompteId(),debitDTO.getMontant(),debitDTO.getDescription());
        return debitDTO;
    }
    @PostMapping("/comptes/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws CompteNonTrouverExceptions {
        this.compteService.credit(creditDTO.getCompteId(),creditDTO.getMontant(),creditDTO.getDescription());
        return creditDTO;
    }
    @PostMapping("/comptes/transfer")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws CompteNonTrouverExceptions, SoldeInsuffisantExeption {
        this.compteService.transfere(
                transferRequestDTO.getCompteSource(),
                transferRequestDTO.getCompteDestination(),
                transferRequestDTO.getMontant());
    }
}
