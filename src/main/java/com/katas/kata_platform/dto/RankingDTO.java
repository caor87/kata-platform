package com.katas.kata_platform.dto;

import lombok.Data;

@Data
public class RankingDTO {
    private String nombre;
    private double puntaje;
    private boolean aprobado;

    public RankingDTO(String nombre, double puntaje, boolean aprobado) {
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.aprobado = aprobado;
    }
}