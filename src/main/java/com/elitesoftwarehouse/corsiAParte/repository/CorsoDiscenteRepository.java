package com.elitesoftwarehouse.corsiAParte.repository;

import com.elitesoftwarehouse.corsiAParte.model.entity.CorsoDiscente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorsoDiscenteRepository extends JpaRepository<CorsoDiscente, Long> {

    // Trova tutte le associazioni per un corso
    List<CorsoDiscente> findByCorsoId(Long corsoId);

    // Elimina tutte le associazioni per un corso
    void deleteByCorsoId(Long corsoId);

    // (Facoltativo) Trova tutte le associazioni per un discente
    List<CorsoDiscente> findByDiscenteId(Long discenteId);
}
