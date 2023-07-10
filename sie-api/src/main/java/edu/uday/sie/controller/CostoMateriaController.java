package edu.uday.sie.controller;

import edu.uday.sie.entity.CostoMateria;
import edu.uday.sie.error.COAException;
import edu.uday.sie.service.CostoMateriaService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("costo-materia")
@Slf4j
public class CostoMateriaController {

    @Autowired
    private CostoMateriaService costoMateriaService;

    @GetMapping
    public ResponseEntity<?> getAllCostoMaterias() {
        log.info("getAllCostoMaterias");
        try {
            return ResponseEntity.ok().body(costoMateriaService.getAllCostoMaterias());
        } catch (COAException coaException) {
            log.warn("Sin datos");
            log.error(coaException.getMessage());
            return new ResponseEntity<>("Datos no encontrados", HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    @PostMapping
    public CostoMateria createCostoMateria(@RequestBody CostoMateria costoMateria) {
        return costoMateriaService.createCostoMateria(costoMateria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCostoMateria(@RequestBody CostoMateria costoMateria) {
        try {
            return ResponseEntity.ok().body(costoMateriaService.updateCostoMateria(costoMateria));
        } catch (COAException coaException) {
            log.warn("Sin datos");
            log.error(coaException.getMessage());
            return new ResponseEntity<>(coaException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCostoMateria(@PathVariable(value = "id") Long id) {
        costoMateriaService.deleteCostoMateria(id);
    }

}
