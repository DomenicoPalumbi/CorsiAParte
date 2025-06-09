package com.elitesoftwarehouse.corsiAParte.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "corso_discente")
public class CorsoDiscente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "corso_id", nullable = false)
    private Long corsoId;

    @Column(name = "discente_id", nullable = false)
    private Long discenteId;

    // Costruttori
    public CorsoDiscente() {}

    public CorsoDiscente(Long corsoId, Long discenteId) {
        this.corsoId = corsoId;
        this.discenteId = discenteId;
    }

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCorsoId() {
        return corsoId;
    }

    public void setCorsoId(Long corsoId) {
        this.corsoId = corsoId;
    }

    public Long getDiscenteId() {
        return discenteId;
    }

    public void setDiscenteId(Long discenteId) {
        this.discenteId = discenteId;
    }
}