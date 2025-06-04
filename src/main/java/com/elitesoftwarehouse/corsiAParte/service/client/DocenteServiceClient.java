package com.elitesoftwarehouse.corsiAParte.service.client;
import com.elitesoftwarehouse.corsiAParte.model.dto.DocenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class DocenteServiceClient {
    private final WebClient webClient;

    @Autowired
    public DocenteServiceClient(@Value("${docenti.service.url}") String docentiServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(docentiServiceUrl)
                .build();
    }
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
    public boolean docenteEsiste(Long docenteId) {
        try {
            DocenteDTO docente = webClient.get()
                    .uri("/docenti/" + docenteId)  // rimosso /api
                    .retrieve()
                    .bodyToMono(DocenteDTO.class)
                    .block();
            return docente != null;
        } catch (Exception e) {
            // Log dell'errore se necessario
            return false;
        }
    }
}