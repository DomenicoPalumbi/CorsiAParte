package com.elitesoftwarehouse.corsiAParte.service.client;

import com.elitesoftwarehouse.corsiAParte.model.dto.DiscenteDTO;
import com.elitesoftwarehouse.corsiAParte.model.entity.CorsoDiscente;
import com.elitesoftwarehouse.corsiAParte.repository.CorsoDiscenteRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class CorsoDiscenteService {

    private final CorsoDiscenteRepository corsoDiscenteRepository;
    private final WebClient webClient;

    public CorsoDiscenteService(CorsoDiscenteRepository corsoDiscenteRepository,
                                WebClient.Builder webClientBuilder) {
        this.corsoDiscenteRepository = corsoDiscenteRepository;
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8080")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Transactional
    public void saveAssociazioni(Long corsoId, List<Long> discenteIds) {
        try {
            corsoDiscenteRepository.deleteByCorsoId(corsoId);

            if (discenteIds != null && !discenteIds.isEmpty()) {
                List<CorsoDiscente> associazioni = discenteIds.stream()
                        .map(discenteId -> {
                            CorsoDiscente cd = new CorsoDiscente();
                            cd.setCorsoId(corsoId);
                            cd.setDiscenteId(discenteId);
                            return cd;
                        })
                        .toList();
                corsoDiscenteRepository.saveAll(associazioni);
            }

            webClient.put()
                    .uri("/discenti/corso/{corsoId}", corsoId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new UpdateDiscenteRequest(discenteIds != null ? discenteIds : List.of()))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

        } catch (WebClientException e) {
            // Log dell'errore ma continuiamo con le modifiche locali
        }
    }

    @Transactional
    public void removeAssociazioni(Long corsoId) {
        corsoDiscenteRepository.deleteByCorsoId(corsoId);
        try {
            webClient.put()
                    .uri("/discenti/corso/{corsoId}", corsoId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new UpdateDiscenteRequest(List.of()))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (WebClientException e) {
            // Log dell'errore
        }
    }

    public List<DiscenteDTO> getDiscentiByCorsoId(Long corsoId) {
        List<Long> discenteIds = corsoDiscenteRepository.findByCorsoId(corsoId)
                .stream()
                .map(CorsoDiscente::getDiscenteId)
                .toList();

        if (discenteIds.isEmpty()) return List.of();

        try {
            return webClient.post()
                    .uri("/discenti/discenti/by-ids")  // Ripristinato il path completo
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(discenteIds)  // Invia direttamente la lista degli ID
                    .retrieve()
                    .bodyToFlux(DiscenteDTO.class)
                    .collectList()
                    .block();
        } catch (WebClientException e) {
            // Log dell'errore
            return List.of();
        }
    }
}

class UpdateDiscenteRequest {
    private List<Long> discenteIds;

    public UpdateDiscenteRequest(List<Long> discenteIds) {
        this.discenteIds = discenteIds;
    }

    public List<Long> getDiscenteIds() {
        return discenteIds;
    }

    public void setDiscenteIds(List<Long> discenteIds) {
        this.discenteIds = discenteIds;
    }
}