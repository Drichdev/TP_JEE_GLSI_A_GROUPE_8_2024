package com.Ega.EgaBankingBackend.dto;

import com.Ega.EgaBankingBackend.Enum.StatusCompte;
import jakarta.persistence.Id;
import lombok.Data;
import org.iban4j.Iban;

import java.util.Date;

@Data
public class CompteCourantDTO extends CompteDTO{
    @Id
    private String Id;
    private double solde = 0.0;
    private Date DateDeCreation;
    private StatusCompte status;
    private static ClientDTO clientDTO;
    private double TauxInteret;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = String.valueOf(id);
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public Date getDateDeCreation() {
        return DateDeCreation;
    }

    public void setDateDeCreation(Date dateDeCreation) {
        DateDeCreation = dateDeCreation;
    }

    public StatusCompte getStatus() {
        return status;
    }

    public void setStatus(StatusCompte status) {
        this.status = status;
    }

    public static ClientDTO getClientDTO() {
        return clientDTO;
    }

    public static void setClientDTO(ClientDTO clientDTO) {
        CompteCourantDTO.clientDTO = clientDTO;
    }

    public double getTauxInteret() {
        return TauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        TauxInteret = tauxInteret;
    }
}

