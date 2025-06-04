
package com.elitesoftwarehouse.corsiAParte.data.dto;

import com.elitesoftwarehouse.corsiAParte.data.entity.Corso;
//import com.example.demo.data.entity.Discente;
//import com.example.demo.data.entity.Docente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CorsoDTO {
    private Long id;
    private String nome;
    private Integer annoAccademico;
    private Long docenteId;
    private String nomeDocente;
    private String cognomeDocente;



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
