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

    public DiscenteDTO getDiscenteById(Long id) {
        try {
            return webClient.get()
                    .uri("/discenti/{id}", id)
                    .retrieve()
                    .bodyToMono(DiscenteDTO.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Impossibile recuperare il discente: " + e.getMessage());
        }
    }

    public List<DiscenteDTO> getAllDiscenti() {
        try {
            return webClient.get()
                    .uri("/discenti")  // Modificato per corrispondere all'endpoint del controller
                    .retrieve()
                    .bodyToFlux(DiscenteDTO.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Impossibile recuperare la lista dei discenti: " + e.getMessage());
        }
    }

    public DiscenteDTO createDiscente(DiscenteDTO discenteDTO) {
        try {
            return webClient.post()
                    .uri("/discenti/nuovo")  // Modificato per corrispondere all'endpoint del controller
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
            // Controlla se esiste già
            DiscenteDTO esistente = getDiscenteByNomeAndCognome(discenteDTO.getNomeDiscente(), discenteDTO.getCognomeDiscente());
            if (esistente != null) {
                return esistente;
            }

            // Se non esiste, lo creiamo
            return webClient.post()
                    .uri("/discenti/nuovo")
                    .bodyValue(discenteDTO)
                    .retrieve()
                    .bodyToMono(DiscenteDTO.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Impossibile gestire il discente: " + e.getMessage());
        }
    }

    private DiscenteDTO getDiscenteByNomeAndCognome(String nome, String cognome) {
        try {
            return webClient.post()
                    .uri("/discenti/cerca")
                    .bodyValue(Map.of(
                            "nome", nome,
                            "cognome", cognome
                    ))
                    .retrieve()
                    .bodyToMono(DiscenteDTO.class)
                    .block();
        } catch (Exception e) {
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
