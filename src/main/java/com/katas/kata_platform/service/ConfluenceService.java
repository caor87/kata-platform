package com.katas.kata_platform.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ConfluenceService {
    private final String confluenceApiUrl = "https://<tu-dominio>.atlassian.net/wiki/rest/api/content";
    private final String confluenceApiToken = "TU_API_TOKEN";

    public void publicarResultados(String contenido) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + confluenceApiToken);
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
        restTemplate.postForObject(confluenceApiUrl, request, String.class);
    }
}
