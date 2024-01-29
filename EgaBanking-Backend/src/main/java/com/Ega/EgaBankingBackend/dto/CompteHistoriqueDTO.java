package com.Ega.EgaBankingBackend.dto;

import lombok.Data;
import org.iban4j.Iban;

import java.util.List;

@Data
public class CompteHistoriqueDTO {
    private String compteId;
    private double solde;
    private int pageCourant;
    private int pageTotal;
    private int taillePage;
    List<OperationDTO> operationDTOS;

    public String getCompteId() {
        return compteId;
    }

    public void setCompteId(String compteId) {
        this.compteId = compteId;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public int getPageCourant() {
        return pageCourant;
    }

    public void setPageCourant(int pageCourant) {
        this.pageCourant = pageCourant;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getTaillePage() {
        return taillePage;
    }

    public void setTaillePage(int taillePage) {
        this.taillePage = taillePage;
    }

    public List<OperationDTO> getOperationDTOS() {
        return operationDTOS;
    }

    public void setOperationDTOS(List<OperationDTO> operationDTOS) {
        this.operationDTOS = operationDTOS;
    }
}
