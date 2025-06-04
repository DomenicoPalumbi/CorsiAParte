package com.elitesoftwarehouse.corsiAParte.client;

import com.elitesoftwarehouse.corsiAParte.data.dto.DocenteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "docente-service", url = "${servizio.docenti.url}")
public interface DocenteClient {

    @GetMapping("/docenti/lista")
    ResponseEntity<List<DocenteDTO>> listaDocenti();

    @GetMapping("/docenti/{id}")
    ResponseEntity<DocenteDTO> getDocenteById(@PathVariable Long id);


    @PostMapping("/docenti/nuovo")
    ResponseEntity<DocenteDTO> saveDocente(@RequestBody DocenteDTO docenteDTO);

    @PutMapping("/docenti/{id}")
    ResponseEntity<DocenteDTO> updateDocente(
            @PathVariable Long id,
            @RequestBody DocenteDTO docenteDTO);

    @DeleteMapping("/docenti/delete/{id}")
    ResponseEntity<Void> deleteDocente(@PathVariable Long id);
}