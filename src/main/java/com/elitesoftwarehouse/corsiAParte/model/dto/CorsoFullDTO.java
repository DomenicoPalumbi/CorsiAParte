package com.elitesoftwarehouse.corsiAParte.model.dto;

import com.elitesoftwarehouse.corsiAParte.model.entity.Corso;
//import com.example.demo.data.entity.Docente;
//import com.example.demo.data.entity.Discente;


public class CorsoFullDTO {
    //private Long id;
    private String nome;
    private Integer annoAccademico;
    //private String nomeCompletoDocente;
    //  private List<String> nomiCompletiDiscenti = new ArrayList<>();
    private Long docenteId;
//    private String nomeDocente;
//    private String cognomeDocente;

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

//    public String getNomeDocente() {
//        return nomeDocente;
//    }
//
//    public void setNomeDocente(String nomeDocente) {
//        this.nomeDocente = nomeDocente;
//    }
//
//    public String getCognomeDocente() {
//        return cognomeDocente;
//    }
//
//    public void setCognomeDocente(String cognomeDocente) {
//        this.cognomeDocente = cognomeDocente;
//    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

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

}
