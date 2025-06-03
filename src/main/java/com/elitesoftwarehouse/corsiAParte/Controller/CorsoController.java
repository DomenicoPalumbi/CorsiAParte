
package com.elitesoftwarehouse.corsiAParte.Controller  ;

import com.elitesoftwarehouse.corsiAParte.data.dto.CorsoDTO;
import com.elitesoftwarehouse.corsiAParte.data.dto.CorsoFullDTO;
import com.elitesoftwarehouse.corsiAParte.service.CorsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/corsi")
public class CorsoController {
    @Autowired
    private CorsoService corsoService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${docenti.url}")
    private String docentiUrl;

    // GET - Ottiene tutti i corsi
    @GetMapping("/lista")
    public ResponseEntity<List<CorsoDTO>> getCorsi() {
        List<CorsoDTO> corsi = corsoService.getAllCorsiDTO();
        return ResponseEntity.ok(corsi);
    }

    @PostMapping("/nuovo")
    public ResponseEntity<CorsoDTO> createCorso(@RequestBody CorsoFullDTO corsoFullDTO) {
        try {
            CorsoDTO nuovoCorso = corsoService.saveCorso(corsoFullDTO);
            return ResponseEntity.ok(nuovoCorso);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorsoDTO> updateCorso(
            @PathVariable Long id,
            @RequestBody CorsoFullDTO corsoFullDTO) {
        try {
            CorsoDTO updated = corsoService.updateCorso(id, corsoFullDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCorso(@PathVariable Long id) {
        try {
            corsoService.deleteCorso(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
