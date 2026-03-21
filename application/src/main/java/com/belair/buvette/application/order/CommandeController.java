package com.belair.buvette.application.order;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
class CommandeController {

    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping(value = "/commandes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCommande(@RequestBody String body) throws IOException {
        if (body == null || body.trim().isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        JsonNode root = mapper.readTree(body);

        JsonNode festivalier = root.get("festivalierId");
        if (festivalier == null || festivalier.asText().isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        JsonNode articles = root.get("articles");
        if (articles == null || !articles.isArray() || articles.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String response = "{\"commandeId\":\"commande-123\",\"status\":\"EN_ATTENTE\"}";
        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(response);
    }

    }


