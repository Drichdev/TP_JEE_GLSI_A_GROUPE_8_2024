package com.Ega.EgaBankingBackend.dto;

import com.Ega.EgaBankingBackend.Enum.OperationType;
import com.Ega.EgaBankingBackend.entity.Compte;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.iban4j.Iban;


import java.util.Date;
@Data
public class OperationDTO {
    private Long Id;
    private Date DateOperation;
    private double montant;
    private OperationType type;
    @ManyToOne
    private Compte compte;
    private String description;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Date getDateOperation() {
        return DateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        DateOperation = dateOperation;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
