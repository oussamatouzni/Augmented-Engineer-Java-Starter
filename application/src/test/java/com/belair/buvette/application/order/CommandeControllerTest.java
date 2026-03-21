package com.belair.buvette.application.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(classes = com.belair.buvette.application.TestConfig.class)
@AutoConfigureMockMvc
class CommandeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PasserCommandeApplicationService service;

    // Scenario: Commande créée avec succès
    @Test
    void shouldCreateCommandeSuccessfully() throws Exception {
        // Given an identified festivalier and available articles
        service.setStock("mojito", 10);
        String payload = "{\"festivalierId\":\"festivalier-42\", \"articles\":[{\"id\": \"mojito\", \"quantite\": 2}]}";

        // When: POST /commandes
        MvcResult result = mvc.perform(post("/commandes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andReturn();

        // Then: expect 201, body contains commandeId and status EN_ATTENTE
        int status = result.getResponse().getStatus();
        String body = result.getResponse().getContentAsString();

        assertThat(status).as("HTTP status for POST /commandes").isEqualTo(201);
        assertThat(body).as("Response should contain commandeId").contains("commandeId");
        assertThat(body).as("Response should contain status EN_ATTENTE").contains("EN_ATTENTE");

        // Persist response JSON for refactor step
        Path outDir = Paths.get("build", "test-output");
        Files.createDirectories(outDir);
        Path outFile = outDir.resolve(CommandeControllerTest.class.getSimpleName() + ".json");
        Files.writeString(outFile, body);
    }

    // Scenario: Requête refusée si le festivalier n'est pas authentifié
    @Test
    void shouldReturn401WhenNotAuthenticated() throws Exception {
        // Given no authenticated festivalier
        MvcResult result = mvc.perform(post("/commandes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertThat(status).as("Unauthenticated requests should be rejected").isEqualTo(401);
    }

    // Scenario: Requête refusée si le corps de la requête est invalide
    @Test
    void shouldReturn400WhenInvalidBody() throws Exception {
        // Given an identified festivalier but missing articles
        String payload = "{\"festivalierId\":\"festivalier-42\"}";

        MvcResult result = mvc.perform(post("/commandes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertThat(status).as("Invalid request body should return 400").isEqualTo(400);
    }

}
