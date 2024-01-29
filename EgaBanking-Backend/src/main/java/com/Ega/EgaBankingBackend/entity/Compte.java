package com.Ega.EgaBankingBackend.entity;

import com.Ega.EgaBankingBackend.Enum.StatusCompte;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.iban4j.CountryCode;
import org.iban4j.Iban;


import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 20)
public abstract class Compte {

    @Id
    @Column(name = "Id_iban",length = 80)
    private String Id;
    private double solde = 0.0;
    private Date DateDeCreation;
    @Enumerated(EnumType.STRING)
    private StatusCompte status;
    @ManyToOne
    private Client client;
    @OneToMany(mappedBy = "compte",fetch = FetchType.LAZY)
    private List<Operation> operations;

    private void Iban(final Iban Id) {
        Iban iban = Iban.random(CountryCode.TG);
        this.Id = String.valueOf(Iban.valueOf(String.valueOf(iban))).toUpperCase();
    }
    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = String.valueOf(id);
    }
    public double getSolde() {
        return solde;
    }

    public void setSolde(Double solde) {
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }
}

