
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
    /*@JsonIgnore
    private Long docenteId;
    private String nomeCompletoDocente;
    private List<String> nomiCompletiDiscenti;

    public CorsoDTO() {
        this.nomiCompletiDiscenti = new ArrayList<>();
    }
*/
    /*public CorsoDTO(Corso corso) {
        this();
        if (corso != null) {
            this.id = corso.getId();
            this.nome = corso.getNome();
            this.annoAccademico = corso.getAnnoAccademico();

            if (corso.getDocente() != null) {
                Docente docente = corso.getDocente();
                this.docenteId = docente.getId();
                this.nomeCompletoDocente = docente.getNome() + " " + docente.getCognome();
            }

            if (corso.getDiscenti() != null) {
                this.nomiCompletiDiscenti = corso.getDiscenti().stream()
                        .map(d -> d.getNome() + " " + d.getCognome())
                        .collect(Collectors.toList());
            }
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

    /*public Long getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(Long docenteId) {
        this.docenteId = docenteId;
    }

    public String getDocenteNomeCompleto() {
        return nomeCompletoDocente;
    }

    public void setDocenteNomeCompleto(String docenteNomeCompleto) {
        this.nomeCompletoDocente = docenteNomeCompleto;
    }

    public List<String> getDiscentiNomiCompleti() {
        return nomiCompletiDiscenti;
    }

    public void setDiscentiNomiCompleti(List<String> nomiCompletiDiscenti) {
        this.nomiCompletiDiscenti = nomiCompletiDiscenti;
    }
*/
}
