package com.Ega.EgaBankingBackend.security;

import com.Ega.EgaBankingBackend.dto.ClientDTO;

import java.util.ArrayList;
import java.util.List;

public class
ClientActive {
    public List<ClientDTO> logins;

    public ClientActive() {
        this.logins = new ArrayList<>();
    }

    public List<ClientDTO> getLogins() {
        return logins;
    }

    public void setLogins(List<ClientDTO> logins) {
        this.logins = logins;
    }
}
