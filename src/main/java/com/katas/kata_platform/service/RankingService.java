package com.katas.kata_platform.service;

import com.katas.kata_platform.dto.RankingDTO;
import com.katas.kata_platform.model.Calificacion;
import com.katas.kata_platform.model.Usuario;
import com.katas.kata_platform.repository.CalificacionRepository;
import com.katas.kata_platform.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final CalificacionRepository calificacionRepository;
    private final UsuarioRepository usuarioRepository;

    public List<RankingDTO> obtenerRanking() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .filter(u -> u.getTipo().equalsIgnoreCase("participante"))
                .map(usuario -> {
                    double puntaje = calcularPuntaje(usuario.getId());
                    boolean aprobado = validarAprobacion(usuario.getId()); // ← se usa aquí
                    return new RankingDTO(usuario.getNombre(), puntaje, aprobado);
                })
                .sorted(Comparator.comparing(RankingDTO::getPuntaje).reversed())
                .collect(Collectors.toList());
    }

    private double calcularPuntaje(Long usuarioId) {
        List<Calificacion> calificaciones = calificacionRepository.findByParticipanteId(usuarioId);
        return calificaciones.stream()
                .mapToDouble(Calificacion::getPuntajeFinal)
                .average()
                .orElse(0);
    }

    private boolean validarAprobacion(Long usuarioId) {
        List<Calificacion> calificaciones = calificacionRepository.findByParticipanteId(usuarioId);
        double puntajeFinal = calificaciones.stream()
                .mapToDouble(Calificacion::getPuntajeFinal)
                .average()
                .orElse(0);
        return puntajeFinal >= 75;
    }
}
