package com.katas.kata_platform.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConfluenceServiceTest {

    private RestTemplate restTemplateMock;
    private ConfluenceService confluenceServiceSpy;

    @BeforeEach
    void setUp() {
        restTemplateMock = mock(RestTemplate.class);
        confluenceServiceSpy = spy(new ConfluenceService() {
            @Override
            public void publicarResultados(String contenido) {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer TU_API_TOKEN");
                headers.set("Content-Type", "application/json");

                Map<String, Object> payload = Map.of(
                        "type", "page",
                        "title", "Resultados de la Kata",
                        "space", Map.of("key", "KATAS"),
                        "body", Map.of(
                                "storage", Map.of(
                                        "value", contenido,
                                        "representation", "storage"
                                )
                        )
                );

                HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
                restTemplateMock.postForObject("https://<tu-dominio>.atlassian.net/wiki/rest/api/content", request, String.class);
            }
        });
    }

    @Test
    void publicarResultados_deberiaEnviarPeticionPOSTConContenido() {
        // Arrange
        String contenido = "<p>Top 3</p>";

        // Act
        confluenceServiceSpy.publicarResultados(contenido);

        // Assert
        ArgumentCaptor<HttpEntity> captor = ArgumentCaptor.forClass(HttpEntity.class);
        verify(restTemplateMock, times(1)).postForObject(anyString(), captor.capture(), eq(String.class));

        HttpEntity<?> requestEntity = captor.getValue();
        Map body = (Map) requestEntity.getBody();

        assertEquals("page", body.get("type"));
        assertEquals("Resultados de la Kata", body.get("title"));

        Map<String, Object> bodyMap = (Map<String, Object>) body.get("body");
        Map<String, String> storage = (Map<String, String>) bodyMap.get("storage");
        assertEquals(contenido, storage.get("value"));
        assertEquals("storage", storage.get("representation"));
    }
}
