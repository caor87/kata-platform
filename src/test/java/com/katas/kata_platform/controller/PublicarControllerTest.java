package com.katas.kata_platform.controller;

import com.katas.kata_platform.dto.RankingDTO;
import com.katas.kata_platform.service.ConfluenceService;
import com.katas.kata_platform.service.RankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PublicarControllerTest {

    private ConfluenceService confluenceService;
    private RankingService rankingService;
    private PublicarController publicarController;

    @BeforeEach
    void setUp() {
        confluenceService = mock(ConfluenceService.class);
        rankingService = mock(RankingService.class);
        publicarController = new PublicarController(confluenceService, rankingService);
    }

    @Test
    void testPublicar() {
        // Arrange
        List<RankingDTO> mockRanking = Arrays.asList(
                new RankingDTO("Ana", 95.5, true),
                new RankingDTO("Luis", 90.0, true),
                new RankingDTO("Laura", 85.0, true),
                new RankingDTO("Carlos", 80.0, false)
        );
        when(rankingService.obtenerRanking()).thenReturn(mockRanking);

        // Act
        ResponseEntity<?> response = publicarController.publicar();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Resultados publicados", response.getBody());

        verify(rankingService, times(1)).obtenerRanking();
        verify(confluenceService, times(1)).publicarResultados(anyString());
    }
}
