package com.katas.kata_platform.controller;

import com.katas.kata_platform.dto.RankingDTO;
import com.katas.kata_platform.service.RankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RankingControllerTest {

    private RankingService rankingService;
    private RankingController rankingController;

    @BeforeEach
    void setUp() {
        rankingService = mock(RankingService.class);
        rankingController = new RankingController(rankingService);
    }

    @Test
    void testObtenerRanking() {
        // Arrange
        RankingDTO r1 = new RankingDTO("Ana", 95.5, true);
        RankingDTO r2 = new RankingDTO("Luis", 82.0, false);
        List<RankingDTO> mockRanking = Arrays.asList(r1, r2);

        when(rankingService.obtenerRanking()).thenReturn(mockRanking);

        // Act
        ResponseEntity<List<RankingDTO>> response = rankingController.obtenerRanking();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        RankingDTO first = response.getBody().get(0);
        assertEquals("Ana", first.getNombre());
        assertEquals(95.5, first.getPuntaje());
        assertTrue(first.isAprobado());

        RankingDTO second = response.getBody().get(1);
        assertEquals("Luis", second.getNombre());
        assertEquals(82.0, second.getPuntaje());
        assertFalse(second.isAprobado());

        verify(rankingService, times(1)).obtenerRanking();
    }
}
