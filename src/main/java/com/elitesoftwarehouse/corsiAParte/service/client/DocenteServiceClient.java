package com.elitesoftwarehouse.corsiAParte.service.client;

import com.elitesoftwarehouse.corsiAParte.model.dto.DocenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Component
public class DocenteServiceClient {
    private final WebClient webClient;

    @Autowired
    public DocenteServiceClient(@Value("${docenti.service.url}") String docentiServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(docentiServiceUrl)
                .build();
    }
    // Metodo per recuperare un docente per ID
    public DocenteDTO getDocenteById(Long docenteId) {
        try {
            return webClient.get()
                    .uri("/docenti/{id}", docenteId)
                    .retrieve()
                    .bodyToMono(DocenteDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            throw new IllegalArgumentException("Docente non trovato con id: " + docenteId);
        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero del docente", e);
        }
    }

    // Metodo per creare un nuovo docente
    private DocenteDTO createDocente(DocenteDTO docenteDTO) {
        return webClient.post()
                .uri("/docenti/nuovo")
                .bodyValue(docenteDTO)  // Passiamo l'oggetto DocenteDTO come corpo della richiesta
                .retrieve()
                .bodyToMono(DocenteDTO.class)
                .block();  // Aspetta la risposta sincrona
    }

    public DocenteDTO getOrCreateDocente(String nomeDocente, String cognomeDocente) {
        // Cerca il docente per nome e cognome
        DocenteDTO docente = getDocenteByNomeAndCognome(nomeDocente, cognomeDocente);

        if (docente == null) {
            // Se non esiste, lo creiamo
            DocenteDTO newDocente = new DocenteDTO();
            newDocente.setNomeDocente(nomeDocente);
            newDocente.setCognomeDocente(cognomeDocente);
            return createDocente(newDocente);
        }

        return docente;
    }


    private DocenteDTO getDocenteByNomeAndCognome(String nome, String cognome) {
        try {
            return webClient.post()
                    .uri("/docenti/cerca")
                    .bodyValue(Map.of(
                            "nome", nome,
                            "cognome", cognome
                    ))
                    .retrieve()
                    .bodyToMono(DocenteDTO.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }



}

