package com.elitesoftwarehouse.corsiAParte.service.client;

import com.elitesoftwarehouse.corsiAParte.model.dto.DocenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    public Mono<DocenteDTO> getDocenteById(Long docenteId) {
        return webClient.get()
                .uri("/docenti/{id}", docenteId)
                .retrieve()
                .bodyToMono(DocenteDTO.class);
    }

    private Mono<DocenteDTO> createDocente(DocenteDTO docenteDTO) {
        return webClient.post()
                .uri("/docenti/nuovo")
                .bodyValue(docenteDTO)
                .retrieve()
                .bodyToMono(DocenteDTO.class);
    }

    public Mono<DocenteDTO> getOrCreateDocente(String nomeDocente, String cognomeDocente) {
        return getDocenteByNomeAndCognome(nomeDocente, cognomeDocente)
                .switchIfEmpty(Mono.defer(() -> {
                    DocenteDTO newDocente = new DocenteDTO();
                    newDocente.setNomeDocente(nomeDocente);
                    newDocente.setCognomeDocente(cognomeDocente);
                    return createDocente(newDocente);
                }));
    }

    private Mono<DocenteDTO> getDocenteByNomeAndCognome(String nome, String cognome) {
        return webClient.post()
                .uri("/docenti/cerca")
                .bodyValue(Map.of(
                        "nome", nome,
                        "cognome", cognome
                ))
                .retrieve()
                .bodyToMono(DocenteDTO.class)
                .onErrorResume(e -> Mono.empty());
    }
}

