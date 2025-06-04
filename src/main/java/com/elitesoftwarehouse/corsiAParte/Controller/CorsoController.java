
package com.elitesoftwarehouse.corsiAParte.Controller  ;

import com.elitesoftwarehouse.corsiAParte.data.dto.CorsoDTO;
import com.elitesoftwarehouse.corsiAParte.data.dto.CorsoFullDTO;
import com.elitesoftwarehouse.corsiAParte.service.CorsoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/corsi")
public class CorsoController {

    @Autowired
    private CorsoService corsoService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/lista")
    public ResponseEntity<List<CorsoDTO>> listaCorsi() {
        return ResponseEntity.ok(corsoService.getAllCorsiDTO());
    }

    @PostMapping("/nuovo")
    public ResponseEntity<?> saveCorso(@RequestBody CorsoFullDTO corsoFullDTO) {
        try {
            CorsoDTO corso = corsoService.saveCorso(corsoFullDTO);
            return ResponseEntity.ok(corso);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<CorsoDTO> updateCorso(
            @PathVariable Long id,
            @RequestBody CorsoFullDTO corsoFullDTO) {
        return ResponseEntity.ok(corsoService.updateCorso(id, corsoFullDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCorso(@PathVariable Long id) {
        corsoService.deleteCorso(id);
        return ResponseEntity.ok(" Elemento eliminato con successo");
    }

    //commento prova
}
