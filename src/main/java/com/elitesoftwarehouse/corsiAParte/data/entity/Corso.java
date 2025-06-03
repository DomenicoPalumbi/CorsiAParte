
package com.elitesoftwarehouse.corsiAParte.data.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "corsi")
public class Corso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "nome")
    private String nome;

    @Column(name = "anno_accademico", nullable = true)
    private Integer annoAccademico;

    @Column( name = "id_doc")
    private Long docenteId;

  /*  @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_doc", referencedColumnName = "id", nullable = false)
    private Docente docente;

    @ManyToMany
    @JoinTable(
            name = "corso_discente",
            joinColumns = @JoinColumn(name = "corso_id"),
            inverseJoinColumns = @JoinColumn(name = "discente_id")
    )
    private List<Discente> discenti;/*

    /* costruttori */
    public Corso() {}

    /*public Corso(String nome, Integer annoAccademico, Docente docente) {
        this.nome = nome;
        this.annoAccademico = annoAccademico;
        this.docente = docente;
    }*/

    /* getter e setter */
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

   /* public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public List<Discente> getDiscenti() {
        return discenti;
    }

    public void setDiscenti(List<Discente> discenti) {
        this.discenti = discenti;
    }*/
}
