package com.belair.buvette.application.order;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PasserCommandeController {

    @PostMapping("/api/orders")
    public ResponseEntity<String> createOrder(@RequestBody String body) {
        return ResponseEntity.status(201).body("{\"orderId\":\"order-123\"}");
    }

}
