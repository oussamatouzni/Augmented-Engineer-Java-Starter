package com.belair.buvette.application;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.belair.buvette.application.order"})
public class TestConfig {
    // Reusable minimal auto-configuration for tests that need a Spring context.

    @org.springframework.context.annotation.Bean
    public com.belair.buvette.domain.order.CommandeRepository inMemoryCommandeRepository() {
        return new TestCommandeRepository();
    }
}

// simple in-memory implementation used only for tests
class TestCommandeRepository implements com.belair.buvette.domain.order.CommandeRepository {
    private final java.util.Map<String, com.belair.buvette.domain.order.Commande> store = new java.util.concurrent.ConcurrentHashMap<>();

    @Override
    public void save(com.belair.buvette.domain.order.Commande c) { store.put(c.getId(), c); }

    @Override
    public java.util.Optional<com.belair.buvette.domain.order.Commande> findById(String id) { return java.util.Optional.ofNullable(store.get(id)); }

    @Override
    public void updateStatus(String id, com.belair.buvette.domain.order.CommandeStatus status) {
        store.computeIfPresent(id, (k, v) -> { v.setStatus(status); return v; });
    }

    @Override
    public void saveForFestivalier(String festivalier, com.belair.buvette.domain.order.Commande c) { save(c); }

    @Override
    public java.util.List<com.belair.buvette.domain.order.Commande> findByFestivalierAndStatus(String festivalier, com.belair.buvette.domain.order.CommandeStatus status) {
        return store.values().stream().filter(c -> c.getStatus() == status).toList();
    }
}
