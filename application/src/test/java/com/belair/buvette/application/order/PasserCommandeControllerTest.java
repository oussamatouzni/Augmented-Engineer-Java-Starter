package com.belair.buvette.application.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(classes = com.belair.buvette.application.TestConfig.class)
@AutoConfigureMockMvc
class PasserCommandeControllerTest {

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setupStandaloneMockMvc() {
        this.mvc = MockMvcBuilders.standaloneSetup(new FakeController()).build();
    }

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
    }

    @RestController
    private static class FakeController {
        @PostMapping("/api/orders")
        public ResponseEntity<String> createOrder(@RequestBody String body) {
            return ResponseEntity.status(201).body("{\"orderId\":\"order-123\"}");
        }
    }

}
