package com.elitesoftwarehouse.corsiAParte.service.client;

import com.elitesoftwarehouse.corsiAParte.model.dto.DiscenteDTO;
import com.elitesoftwarehouse.corsiAParte.model.entity.CorsoDiscente;
import com.elitesoftwarehouse.corsiAParte.repository.CorsoDiscenteRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

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
                    .bodyValue(discenteIds != null ? discenteIds : List.of())
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

        } catch (WebClientException e) {
            // gestione errore
        }
    }

    @Transactional
    public void removeAssociazioni(Long corsoId) {
        corsoDiscenteRepository.deleteByCorsoId(corsoId);
        try {
            webClient.put()
                    .uri("/discenti/corso/{corsoId}", corsoId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(List.of())
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (WebClientException e) {
            // Log dell'errore
        }
    }

    public Mono<List<DiscenteDTO>> getDiscentiByCorsoId(Long corsoId) {
        List<Long> discenteIds = corsoDiscenteRepository.findByCorsoId(corsoId)
                .stream()
                .map(CorsoDiscente::getDiscenteId)
                .toList();

        if (discenteIds.isEmpty()) return Mono.just(List.of());

        return webClient.post()
                .uri("/discenti/discenti/by-ids")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(discenteIds)
                .retrieve()
                .bodyToFlux(DiscenteDTO.class)
                .collectList()
                .onErrorResume(e -> {
                    // Log dell'errore
                    return Mono.just(List.of());
                });
    }
}