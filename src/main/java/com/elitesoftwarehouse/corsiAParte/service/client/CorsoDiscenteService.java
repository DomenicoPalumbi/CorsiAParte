package com.elitesoftwarehouse.corsiAParte.service.client;

import com.elitesoftwarehouse.corsiAParte.model.dto.DiscenteDTO;
import com.elitesoftwarehouse.corsiAParte.model.entity.CorsoDiscente;
import com.elitesoftwarehouse.corsiAParte.repository.CorsoDiscenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorsoDiscenteService {

    private final CorsoDiscenteRepository corsoDiscenteRepository;
    private final WebClient webClient;

    public CorsoDiscenteService(CorsoDiscenteRepository corsoDiscenteRepository,
                                WebClient.Builder webClientBuilder) {
        this.corsoDiscenteRepository = corsoDiscenteRepository;
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8080")  // microservizio studenti
                .build();
    }

    public void saveAssociazioni(Long corsoId, List<Long> discenteIds) {
        // Rimuovi le vecchie associazioni
        corsoDiscenteRepository.deleteByCorsoId(corsoId);

        // Salva le nuove
        List<CorsoDiscente> associazioni = discenteIds.stream()
                .map(discenteId -> new CorsoDiscente(corsoId, discenteId))
                .toList();

        corsoDiscenteRepository.saveAll(associazioni);
    }

    public void removeAssociazioni(Long corsoId) {
        corsoDiscenteRepository.deleteByCorsoId(corsoId);
    }

    public List<DiscenteDTO> getDiscentiByCorsoId(Long corsoId) {
        List<Long> discenteIds = corsoDiscenteRepository.findByCorsoId(corsoId)
                .stream()
                .map(CorsoDiscente::getDiscenteId)
                .toList();

        if (discenteIds.isEmpty()) return List.of();

        return webClient.post()
                .uri("/discenti/discenti/by-ids")
                .bodyValue(discenteIds)
                .retrieve()
                .bodyToFlux(DiscenteDTO.class)
                .collectList()
                .block();
    }
}
