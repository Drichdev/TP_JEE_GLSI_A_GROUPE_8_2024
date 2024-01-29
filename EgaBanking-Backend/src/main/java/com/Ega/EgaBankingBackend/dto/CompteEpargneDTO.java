package com.Ega.EgaBankingBackend.dto;

import com.Ega.EgaBankingBackend.Enum.StatusCompte;
import jakarta.persistence.*;
import lombok.Data;
import org.iban4j.Iban;

import java.util.Date;

@Data
public class CompteEpargneDTO extends CompteDTO{
    @Id
    private String Id;
    private double solde = 0.0;
    private Date DateDeCreation;

    private StatusCompte status;

    private ClientDTO clientDTO;
    private double decouvert;

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

    public ClientDTO getClientDTO() {
        return clientDTO;
    }

    public void setClientDTO(ClientDTO clientDTO) {
        this.clientDTO = clientDTO;
    }

    public double getDecouvert() {
        return decouvert;
    }

    public void setDecouvert(double decouvert) {
        this.decouvert = decouvert;
    }
}

