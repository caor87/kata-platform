package com.katas.kata_platform.controller;

import com.katas.kata_platform.model.Calificacion;
import com.katas.kata_platform.repository.CalificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/calificaciones")
public class CalificacionController {
    private final CalificacionRepository calificacionRepository;

    @PostMapping
    public ResponseEntity<Calificacion> registrarCalificacion(@RequestBody Calificacion calificacion) {
        return ResponseEntity.ok(calificacionRepository.save(calificacion));
    }
}