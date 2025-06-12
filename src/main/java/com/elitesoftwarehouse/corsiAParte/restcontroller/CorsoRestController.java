
package com.elitesoftwarehouse.corsiAParte.restcontroller;

import com.elitesoftwarehouse.corsiAParte.mapper.CorsoMapper;
import com.elitesoftwarehouse.corsiAParte.model.dto.CorsoDTO;
import com.elitesoftwarehouse.corsiAParte.model.dto.CorsoFullDTO;
import com.elitesoftwarehouse.corsiAParte.model.dto.DiscenteDTO;
import com.elitesoftwarehouse.corsiAParte.model.entity.Corso;
import com.elitesoftwarehouse.corsiAParte.repository.CorsoRepository;
import com.elitesoftwarehouse.corsiAParte.service.CorsoService;
import com.elitesoftwarehouse.corsiAParte.service.client.CorsoDiscenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/corsi")
public class CorsoRestController {

    @Autowired
    private CorsoService corsoService;
    @Autowired
    private CorsoRepository corsoRepository;
    @Autowired
    private CorsoMapper corsoMapper;
    @Autowired
    private CorsoDiscenteService corsoDiscenteService;

    @PostMapping("/nuovo")
    public ResponseEntity<CorsoDTO> saveCorso(@RequestBody CorsoFullDTO corsoFullDTO) {
        try {
            CorsoDTO nuovoCorso = corsoService.saveCorso(corsoFullDTO);
            return ResponseEntity.ok(nuovoCorso);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CorsoDTO> updateCorso(
            @PathVariable Long id,
            @RequestBody CorsoFullDTO corsoFullDTO) {
        try {
            CorsoDTO updatedCorso = corsoService.updateCorso(id, corsoFullDTO);
            return ResponseEntity.ok(updatedCorso);
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/lista")
    public List<CorsoDTO> getAllCorsi() {
        // Recuperiamo tutti i corsi dal repository
        List<Corso> corsi = corsoRepository.findAll();

        // Creiamo una lista di DTO dei corsi con i discenti associati
        return corsi.stream()
                .map(corso -> {
                    CorsoDTO corsoDTO = corsoMapper.toDto(corso);

                    // Recuperiamo i discenti associati al corso
                    List<DiscenteDTO> discenti = corsoDiscenteService.getDiscentiByCorsoId(corso.getId());
                    corsoDTO.setDiscenti(discenti);

                    return corsoDTO;
                })
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CorsoDTO> getCorsoById(@PathVariable Long id) {
        try {
            CorsoDTO corsoDTO = corsoService.getCorsoById(id);
            return ResponseEntity.ok(corsoDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
