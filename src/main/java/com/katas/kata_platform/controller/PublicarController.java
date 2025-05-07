package com.katas.kata_platform.controller;

import com.katas.kata_platform.dto.RankingDTO;
import com.katas.kata_platform.service.ConfluenceService;
import com.katas.kata_platform.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/publicar")
@RequiredArgsConstructor
public class PublicarController {
    private final ConfluenceService confluenceService;
    private final RankingService rankingService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping
    public ResponseEntity<?> publicar() {
        List<RankingDTO> ranking = rankingService.obtenerRanking();
        String contenido = generarContenido(ranking);
        confluenceService.publicarResultados(contenido);
        return ResponseEntity.ok("Resultados publicados");
    }

    private String generarContenido(List<RankingDTO> ranking) {
        StringBuilder contenido = new StringBuilder("<p>Top 3 finalistas:</p>");
        for (int i = 0; i < 3 && i < ranking.size(); i++) {
            contenido.append("<p>").append(i + 1).append(". ").append(ranking.get(i).getNombre()).append(" - ").append(ranking.get(i).getPuntaje()).append("</p>");
        }
        return contenido.toString();
    }
}
