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

    // Use the Spring context-provided MockMvc (no standalone fake controller)

    @Test
    void shouldCreateOrderWhenItemAvailable() throws Exception {
        // Given: an identified festival goer and an available item "Mojito"
        String payload = "{\"customerId\":\"user-123\", \"items\":[{\"name\":\"Mojito\", \"quantity\":1}]}";

        // When: the festival goer places an order for 1 "Mojito"
        MvcResult result = mvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andReturn();

        // Then: the order is created and a command id is returned (expecting 201)
        int status = result.getResponse().getStatus();
        String body = result.getResponse().getContentAsString();

        assertThat(status).as("HTTP status for POST /api/orders").isEqualTo(201);
        assertThat(body).as("Response body should contain an order id").contains("orderId");

        // Persist response JSON for the upcoming refactor step using the test class name
        Path outDir = Paths.get("build", "test-output");
        Files.createDirectories(outDir);
        Path outFile = outDir.resolve(CommandeControllerTest.class.getSimpleName() + ".json");
        Files.writeString(outFile, body);
    }

    // Inline fake removed: production controller `PasserCommandeController` is used instead.

}
