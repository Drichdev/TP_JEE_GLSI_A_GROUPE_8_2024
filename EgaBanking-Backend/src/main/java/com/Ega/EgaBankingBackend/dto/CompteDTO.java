package com.Ega.EgaBankingBackend.dto;

import lombok.Data;

@Data
public class CompteDTO {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
