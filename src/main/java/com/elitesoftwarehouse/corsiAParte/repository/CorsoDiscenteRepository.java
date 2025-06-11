package com.elitesoftwarehouse.corsiAParte.repository;

import com.elitesoftwarehouse.corsiAParte.model.entity.CorsoDiscente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorsoDiscenteRepository extends JpaRepository<CorsoDiscente, Long> {


    List<CorsoDiscente> findByCorsoId(Long corsoId);


    void deleteByCorsoId(Long corsoId);


    List<CorsoDiscente> findByDiscenteId(Long discenteId);
}
