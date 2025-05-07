package com.katas.kata_platform.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Service
public class ConfluenceService {

    // Tu dominio Confluence: reemplaza "caoralejayese" si es diferente
    private final String confluenceApiUrl = "https://caoralejayese.atlassian.net/wiki/spaces/KATA/overview?homepageId=131439";

    // Tu email de Atlassian
    private final String atlassianEmail = "caoralejayese@gmail.com";

    // Tu token de API (mejor moverlo a una variable de entorno)
    private final String confluenceApiToken = "";

    public void publicarResultados(String contenido) {
        RestTemplate restTemplate = new RestTemplate();

        // Codificar en base64 el email + token
        String auth = atlassianEmail + ":" + confluenceApiToken;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> payload = Map.of(
                "type", "page",
                "title", "Resultados de la Kata",
                "space", Map.of("key", "KATA"),
                "body", Map.of(
                        "storage", Map.of(
                                "value", contenido,
                                "representation", "storage"
                        )
                )
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
        String response = restTemplate.postForObject(confluenceApiUrl, request, String.class);

        System.out.println("Respuesta de Confluence: " + response);
    }
}
