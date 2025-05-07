package com.katas.kata_platform.service;

import com.katas.kata_platform.dto.RankingDTO;
import com.katas.kata_platform.model.Calificacion;
import com.katas.kata_platform.model.Usuario;
import com.katas.kata_platform.repository.CalificacionRepository;
import com.katas.kata_platform.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RankingServiceTest {

    private CalificacionRepository calificacionRepository;
    private UsuarioRepository usuarioRepository;
    private RankingService rankingService;

    @BeforeEach
    void setUp() {
        calificacionRepository = mock(CalificacionRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        rankingService = new RankingService(calificacionRepository, usuarioRepository);
    }

    @Test
    void obtenerRanking_deberiaRetornarRankingOrdenadoYConAprobacion() {
        // Arrange
        Usuario participante1 = new Usuario();
        participante1.setId(1L);
        participante1.setNombre("Alice");
        participante1.setTipo("participante");

        Usuario participante2 = new Usuario();
        participante2.setId(2L);
        participante2.setNombre("Bob");
        participante2.setTipo("participante");

        Usuario jurado = new Usuario();
        jurado.setId(3L);
        jurado.setNombre("Carlos");
        jurado.setTipo("jurado");

        when(usuarioRepository.findAll()).thenReturn(List.of(participante1, participante2, jurado));

        Calificacion c1 = new Calificacion();
        c1.setPerfil(8); c1.setComunicacion(9); c1.setTecnico(10); c1.setExtra(1); // ≈ 10.0
        Calificacion c2 = new Calificacion();
        c2.setPerfil(6); c2.setComunicacion(7); c2.setTecnico(8); c2.setExtra(0); // ≈ 7.15

        when(calificacionRepository.findByParticipanteId(1L)).thenReturn(List.of(c1, c1)); // ≈ 10
        when(calificacionRepository.findByParticipanteId(2L)).thenReturn(List.of(c2));     // ≈ 7.15

        // Act
        List<RankingDTO> ranking = rankingService.obtenerRanking();

        // Assert
        assertEquals(2, ranking.size());

        RankingDTO primero = ranking.get(0);
        RankingDTO segundo = ranking.get(1);

        assertEquals("Alice", primero.getNombre());
        assertTrue(primero.isAprobado());
        assertTrue(primero.getPuntaje() > segundo.getPuntaje());

        assertEquals("Bob", segundo.getNombre());
        assertFalse(segundo.isAprobado());
    }
}
