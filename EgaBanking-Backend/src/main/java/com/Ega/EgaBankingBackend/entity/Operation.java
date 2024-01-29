package com.Ega.EgaBankingBackend.entity;
import com.Ega.EgaBankingBackend.Enum.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.NoArgsConstructor;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Operation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Date DateOperation;
    private double montant;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20)
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
