package com.belair.buvette.application.order;

import com.belair.buvette.domain.order.Commande;
import com.belair.buvette.domain.order.PasserCommandeUseCase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
class CommandeController {

    private final PasserCommandeApplicationService service;
    private final ObjectMapper mapper = new ObjectMapper();

    CommandeController(PasserCommandeApplicationService service) {
        this.service = service;
    }

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
        if (articles == null || !articles.isArray() || articles.size() == 0) {
            return ResponseEntity.badRequest().build();
        }

        List<PasserCommandeUseCase.OrderLine> lines = new ArrayList<>();
        for (JsonNode a : articles) {
            String id = a.has("id") ? a.get("id").asText() : a.get("name").asText();
            int qty = a.has("quantite") ? a.get("quantite").asInt() : a.get("quantity").asInt();
            lines.add(new PasserCommandeUseCase.OrderLine(id, qty));
        }

        Commande commande = service.createCommande(festivalier.asText(), lines);

        String response = mapper.writeValueAsString(
                java.util.Map.of("commandeId", commande.getId(), "status", commande.getStatus().toString())
        );
        return ResponseEntity.status(201).contentType(MediaType.APPLICATION_JSON).body(response);
    }

}


