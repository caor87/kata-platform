package com.katas.kata_platform.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long participanteId;
    private Long juradoId;
    private int perfil;        // Calificación sobre perfil (10%)
    private int comunicacion;  // Calificación sobre comunicación (35%)
    private int tecnico;       // Calificación sobre técnico (55%)
    private int extra;         // Calificación extra que da el jurado

    public double getPuntajeFinal() {
        double base = perfil * 0.10 + comunicacion * 0.35 + tecnico * 0.55;
        return base + extra;
    }
}
