package com.elitesoftwarehouse.corsiAParte.data.dto;

import com.elitesoftwarehouse.corsiAParte.data.entity.Corso;

public class CorsoFullDTO {
    private Long id;
    private String nome;
    private Integer annoAccademico;
    private Long docenteId;
    private String nomeDocente;
    private String cognomeDocente;




    public CorsoFullDTO() {
    }

    public CorsoFullDTO(Corso corso) {
        this.id = corso.getId();
        this.nome = corso.getNome();
        this.annoAccademico = corso.getAnnoAccademico();
        this.docenteId = corso.getDocenteId();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnnoAccademico() {
        return annoAccademico;
    }

    public void setAnnoAccademico(Integer annoAccademico) {
        this.annoAccademico = annoAccademico;
    }
    public Long getDocenteId() {
        return docenteId;
    }
    public void setDocenteId(Long docenteId) {
        this.docenteId = docenteId;
    }

    public String getNomeDocente() {
        return nomeDocente;
    }

    public void setNomeDocente(String nomeDocente) {
        this.nomeDocente = nomeDocente;
    }

    public String getCognomeDocente() {
        return cognomeDocente;
    }

    public void setCognomeDocente(String cognomeDocente) {
        this.cognomeDocente = cognomeDocente;
    }
}
