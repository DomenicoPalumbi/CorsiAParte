package com.elitesoftwarehouse.corsiAParte.model.dto;

public class DiscenteDTO {
    private Long id;
    private String nomeDiscente;
    private String cognomeDiscente;

    public DiscenteDTO() {}

    public DiscenteDTO(Long id,String nomeDiscente , String cognomeDiscente) {
        this.id = id;
        this.nomeDiscente = nomeDiscente;
        this.cognomeDiscente = cognomeDiscente;
    }
    public DiscenteDTO(String nomeDiscente, String cognomeDiscente) {
        this.nomeDiscente = nomeDiscente;
        this.cognomeDiscente = cognomeDiscente;
    }

    // getter e setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeDiscente() { return nomeDiscente; }
    public void setNomeDiscente(String nomeDiscente) { this.nomeDiscente = nomeDiscente; }

    public String getCognomeDiscente() { return cognomeDiscente; }
    public void setCognomeDiscente(String cognomeDiscente) { this.cognomeDiscente = cognomeDiscente; }
}