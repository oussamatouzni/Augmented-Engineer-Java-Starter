package com.belair.buvette.application.order;

import com.belair.buvette.domain.order.ICommandeRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
class InMemoryStockAdapter implements ICommandeRepository {
    private final Map<String, Integer> store = new ConcurrentHashMap<>();

    void set(String id, int qty) { store.put(id, qty); }

    @Override
    public boolean exists(String articleId) {
        return store.containsKey(articleId);
    }

    @Override
    public int availableQuantity(String articleId) {
        return store.getOrDefault(articleId, 0);
    }

    @Override
    public void decrement(String articleId, int quantity) {
        store.computeIfPresent(articleId, (k, v) -> v - quantity);
    }
}
