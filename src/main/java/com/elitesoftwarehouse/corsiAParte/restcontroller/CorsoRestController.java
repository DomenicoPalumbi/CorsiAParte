package com.elitesoftwarehouse.corsiAParte.restcontroller;

import com.elitesoftwarehouse.corsiAParte.model.dto.CorsoDTO;
import com.elitesoftwarehouse.corsiAParte.model.dto.CorsoFullDTO;
import com.elitesoftwarehouse.corsiAParte.service.CorsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/corsi")
public class CorsoRestController {

    private final CorsoService corsoService;

    @Autowired
    public CorsoRestController(CorsoService corsoService) {
        this.corsoService = corsoService;
    }

    @PostMapping("/nuovo")
    public Mono<ResponseEntity<CorsoDTO>> createCorso(@RequestBody CorsoFullDTO corsoFullDTO) {
        return corsoService.saveCorso(corsoFullDTO)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<CorsoDTO>> updateCorso(@PathVariable Long id, @RequestBody CorsoFullDTO corsoFullDTO) {
        return corsoService.updateCorso(id, corsoFullDTO)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteCorso(@PathVariable Long id) {
        try {
            corsoService.deleteCorso(id);
            return Mono.just(ResponseEntity.ok().build());
        } catch (Exception e) {
            return Mono.just(ResponseEntity.notFound().build());
        }
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CorsoDTO>> getCorsoById(@PathVariable Long id) {
        return corsoService.getCorsoById(id)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/lista")
    public Mono<ResponseEntity<List<CorsoDTO>>> getAllCorsi() {
        return corsoService.getAllCorsi()
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }
}

