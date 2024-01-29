package com.Ega.EgaBankingBackend.repository;

import com.Ega.EgaBankingBackend.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface LogRepository extends JpaRepository<Log,Long> {
    @Query(value = "select * from logs l  where date_action >= ?1  and date_action <= ?2 order  by date_action desc", nativeQuery = true)
    List<Log> findByPeriode(Date debut, Date fin);
}
