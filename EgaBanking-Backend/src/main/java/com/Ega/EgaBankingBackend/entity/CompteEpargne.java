package com.Ega.EgaBankingBackend.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@DiscriminatorValue("CompteEpargne")
public class CompteEpargne extends Compte{
    private double TauxInteret;

    public double getTauxInteret() {
        return TauxInteret;
    }

    public void setTauxInteret(double tauxInteret) {
        TauxInteret = tauxInteret;
    }

}

