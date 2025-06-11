package com.elitesoftwarehouse.corsiAParte.service.client;

import com.elitesoftwarehouse.corsiAParte.model.dto.DiscenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class DiscenteServiceClient {
    private final WebClient webClient;

    @Autowired
    public DiscenteServiceClient(@Value("${discenti.service.url}") String discentiServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(discentiServiceUrl)
                .build();
    }

    public DiscenteDTO createDiscente(DiscenteDTO discenteDTO) {
        try {
            // Controlla se il discente esiste già nel sistema prima di crearlo
            DiscenteDTO esistente = getDiscenteByNomeAndCognome(discenteDTO.getNomeDiscente(), discenteDTO.getCognomeDiscente());
            if (esistente != null) {
                // Se esiste già, restituisci il discente esistente
                return esistente;
            }
            // Se non esiste, procedi con la creazione
            return webClient.post()
                    .uri("/discenti/nuovo")
                    .bodyValue(discenteDTO)
                    .retrieve()
                    .bodyToMono(DiscenteDTO.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Impossibile creare il discente: " + e.getMessage());
        }
    }

    public DiscenteDTO getOrCreateDiscente(DiscenteDTO discenteDTO) {
        try {
            // Verifica se esiste già un discente con nome e cognome
            DiscenteDTO esistente = getDiscenteByNomeAndCognome(discenteDTO.getNomeDiscente(), discenteDTO.getCognomeDiscente());

            // Se esiste, restituisci il discente esistente
            if (esistente != null) {
                return esistente;
            }

            // Se non esiste, crea un nuovo discente
            return createDiscente(discenteDTO);
        } catch (Exception e) {
            throw new RuntimeException("Impossibile gestire il discente: " + e.getMessage());
        }
    }


    private DiscenteDTO getDiscenteByNomeAndCognome(String nomeDiscente, String cognomeDiscente) {
        try {
            // Chiamata al microservizio per cercare un discente con nome e cognome
            return webClient.post()
                    .uri("/discenti/cerca")
                    .bodyValue(Map.of("nomeDiscente", nomeDiscente, "cognomeDiscente", cognomeDiscente))
                    .retrieve()
                    .bodyToMono(DiscenteDTO.class)
                    .block();
        } catch (Exception e) {
            // Se c'è un errore o nessun risultato, ritorna null
            return null;
        }
    }
    public DiscenteDTO updateDiscente(Long id, DiscenteDTO discenteDTO) {
        try {
            return webClient.put()
                    .uri("/discenti/{id}", id)
                    .bodyValue(discenteDTO)
                    .retrieve()
                    .bodyToMono(DiscenteDTO.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Impossibile aggiornare il discente: " + e.getMessage());
        }
    }

    public void deleteDiscente(Long id) {
        try {
            webClient.delete()
                    .uri("/discenti/delete/{id}", id)  // Modificato per corrispondere all'endpoint del controller
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Impossibile eliminare il discente: " + e.getMessage());
        }
    }

}
