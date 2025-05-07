package com.katas.kata_platform.controller;

import com.katas.kata_platform.model.Calificacion;
import com.katas.kata_platform.repository.CalificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalificacionControllerTest {

    private CalificacionRepository calificacionRepository;
    private CalificacionController calificacionController;

    @BeforeEach
    void setUp() {
        calificacionRepository = mock(CalificacionRepository.class);
        calificacionController = new CalificacionController(calificacionRepository);
    }

    @Test
    void registrarCalificacion_deberiaGuardarYRetornarLaCalificacion() {
        // Arrange
        Calificacion calificacion = new Calificacion();
        calificacion.setParticipanteId(1L);
        calificacion.setJuradoId(2L);
        calificacion.setPerfil(8);
        calificacion.setComunicacion(9);
        calificacion.setTecnico(10);
        calificacion.setExtra(1);

        when(calificacionRepository.save(any(Calificacion.class))).thenReturn(calificacion);

        // Act
        ResponseEntity<Calificacion> response = calificacionController.registrarCalificacion(calificacion);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getParticipanteId());
        assertEquals(2L, response.getBody().getJuradoId());
        assertEquals(1.0 + 8 * 0.10 + 9 * 0.35 + 10 * 0.55, response.getBody().getPuntajeFinal(), 0.01);

        verify(calificacionRepository, times(1)).save(calificacion);
    }
}
