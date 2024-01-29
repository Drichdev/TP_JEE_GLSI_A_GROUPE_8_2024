package com.Ega.EgaBankingBackend.dto;

import org.iban4j.Iban;

public class TransferRequestDTO {
    private String compteSource;
    private String compteDestination;
    private double montant;
    private String description;
    public String getCompteSource() {
        return compteSource;
    }

    public void setCompteSource(Iban compteSource) {
        this.compteSource = String.valueOf(compteSource);
    }

    public String getCompteDestination() {
        return compteDestination;
    }

    public void setCompteDestination(Iban compteDestination) {
        this.compteDestination = String.valueOf(compteDestination);
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

