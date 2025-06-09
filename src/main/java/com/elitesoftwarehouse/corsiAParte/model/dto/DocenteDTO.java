package com.elitesoftwarehouse.corsiAParte.model.dto;



public class DocenteDTO {
    private Long id;
    private String nomeDocente;
    private String cognomeDocente;
    private String emailDocente;

    public DocenteDTO() {
    }


    public DocenteDTO(Long id, String nomeDocente, String cognomeDocente, String emailDocente) {
        this.id = id;
        this.nomeDocente = nomeDocente;
        this.cognomeDocente = cognomeDocente;
        this.emailDocente = emailDocente;

    }

    public DocenteDTO(String nomeDocente, String cognomeDocente, String emailDocente) {
        this.nomeDocente = nomeDocente;
        this.cognomeDocente = cognomeDocente;
        this.emailDocente = emailDocente;
    }

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmailDocente() {
        return emailDocente;
    }

    public void setEmailDocente(String emailDocente) {
        this.emailDocente = emailDocente;
    }

}