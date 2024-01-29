package com.Ega.EgaBankingBackend.dto;

import lombok.Data;
import org.iban4j.Iban;

@Data
public class DebitDTO {
    private String compteId;
    private double montant;
    private  String description;

    public String getCompteId() {
        return compteId;
    }

    public void setCompteId(String compteId) {
        this.compteId = String.valueOf(compteId);
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
