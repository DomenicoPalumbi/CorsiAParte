
package com.elitesoftwarehouse.corsiAParte.RestController;

import com.elitesoftwarehouse.corsiAParte.mapper.CorsoMapper;
import com.elitesoftwarehouse.corsiAParte.model.dto.CorsoDTO;
import com.elitesoftwarehouse.corsiAParte.model.dto.CorsoFullDTO;
import com.elitesoftwarehouse.corsiAParte.service.CorsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/corsi")
public class CorsoRestController {

    @Autowired
    private CorsoService corsoService;
    @Autowired
    private CorsoMapper corsoMapper;

    @GetMapping("/lista")
    public ResponseEntity<List<CorsoDTO>> getCorsi() {
        List<CorsoDTO> corsi = corsoService.getAllCorsiDTO();
        return ResponseEntity.ok(corsi);
    }

    @PostMapping("/nuovo")
    public ResponseEntity<?> createCorso(@RequestBody CorsoFullDTO corsoFullDTO) {
        try {
            CorsoDTO nuovoCorso = corsoService.saveCorso(corsoFullDTO);
            return ResponseEntity.ok(nuovoCorso);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCorso(
            @PathVariable Long id,
            @RequestBody CorsoFullDTO corsoFullDTO) {
        try {
            CorsoDTO updated = corsoService.updateCorso(id, corsoFullDTO);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Errore di validazione: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Errore: " + e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCorso(@PathVariable Long id) {
        try {
            corsoService.deleteCorso(id);
            return ResponseEntity.ok("Corso eliminato con successo.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Corso non trovato con id: " + id);
        }
    }
}
