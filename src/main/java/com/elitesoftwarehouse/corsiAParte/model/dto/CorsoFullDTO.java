package com.elitesoftwarehouse.corsiAParte.model.dto;

import com.elitesoftwarehouse.corsiAParte.model.entity.Corso;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
//import com.example.demo.data.entity.Docente;
//import com.example.demo.data.entity.Discente;


public class CorsoFullDTO {
    @JsonIgnore
    private Long id;
    private String nome;
    private Integer annoAccademico;
    @JsonIgnore
    private Long docenteId;
    private String nomeDocente;
    private String cognomeDocente;
    @JsonIgnore
    private String emailDocente;
    private List<DiscenteDTO> discenti;


    public CorsoFullDTO() {
    }

    public CorsoFullDTO(Corso corso) {
        //this.id = corso.getId();
        this.nome = corso.getNome();
        this.annoAccademico = corso.getAnnoAccademico();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailDocente() {
        return emailDocente;
    }

    public void setEmailDocente(String emailDocente) {
        this.emailDocente = emailDocente;
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

    public void setDocenteId(long docenteId) {
        this.docenteId = docenteId;
    }


    public List<DiscenteDTO> getDiscenti() {
        return discenti;
    }

    public void setDiscenti(List<DiscenteDTO> discenti) {
        this.discenti = discenti;
    }
}
