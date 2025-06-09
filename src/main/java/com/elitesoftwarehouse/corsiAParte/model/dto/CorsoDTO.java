
package com.elitesoftwarehouse.corsiAParte.model.dto;

//import com.example.demo.data.entity.Discente;
//import com.example.demo.data.entity.Docente;


import java.util.List;

public class CorsoDTO {
    private Long id;
    private String nome;
    private Integer annoAccademico;
    private Long docenteId;
    private String nomeDocente;
    private String cognomeDocente;
    private List<DiscenteDTO> discenti;


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

//    public String getNomeDiscente() {
//        return nomeDiscente;
//    }
//
//    public void setNomeDiscente(String nomeDiscente) {
//        this.nomeDiscente = nomeDiscente;
//    }
//
//    public String getCognomeDiscente() {
//        return cognomeDiscente;
//    }
//
//    public void setCognomeDiscente(String cognomeDiscente) {
//        this.cognomeDiscente = cognomeDiscente;
//    }

    public List<DiscenteDTO> getDiscenti() {
        return discenti;
    }

    public void setDiscenti(List<DiscenteDTO> discenti) {
        this.discenti = discenti;
    }
}
