package com.Ega.EgaBankingBackend.repository;

import com.Ega.EgaBankingBackend.entity.Compte;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CompteRepository extends JpaRepository<Compte, String> { }
