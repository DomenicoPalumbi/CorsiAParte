package com.elitesoftwarehouse.corsiAParte.data.dto;

import com.elitesoftwarehouse.corsiAParte.data.entity.Corso;
//import com.example.demo.data.entity.Docente;
//import com.example.demo.data.entity.Discente;

import java.util.ArrayList;
import java.util.List;

public class CorsoFullDTO {
    private Long id;
    private String nome;
    private Integer annoAccademico;
    //private String nomeCompletoDocente;
  //  private List<String> nomiCompletiDiscenti = new ArrayList<>();
    private long docenteId;

    public CorsoFullDTO() {
    }

    public CorsoFullDTO(Corso corso) {
        this.id = corso.getId();
        this.nome = corso.getNome();
        this.annoAccademico = corso.getAnnoAccademico();
    }
/*
        if (corso.getDocente() != null) {
            this.nomeCompletoDocente = String.format("%s %s",
                    corso.getDocente().getNome(),
                    corso.getDocente().getCognome());
        }

        if (corso.getDiscenti() != null) {
            this.nomiCompletiDiscenti = corso.getDiscenti().stream()
                    .map(d -> String.format("%s %s", d.getNome(), d.getCognome()))
                    .toList();
        }
    }*/

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
    public long getDocenteId() {
        return docenteId;
    }
    public void setDocenteId(long docenteId) {
        this.docenteId = docenteId;
    }

    /*public String getNomeCompletoDocente() {
        return nomeCompletoDocente;
    }

    public void setNomeCompletoDocente(String nomeCompletoDocente) {
        this.nomeCompletoDocente = nomeCompletoDocente;
    }

    public List<String> getNomiCompletiDiscenti() {
        return nomiCompletiDiscenti;
    }

    public void setNomiCompletiDiscenti(List<String> nomiCompletiDiscenti) {
        this.nomiCompletiDiscenti = nomiCompletiDiscenti;
    }*/
}
