package com.belair.buvette.domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PasserCommandeUseCaseTest {

    private InMemoryStockPort stock;
    private PasserCommandeUseCase useCase;

    @BeforeEach
    void setUp() {
        stock = new InMemoryStockPort();
        useCase = new PasserCommandeUseCase(stock);
    }

    @Test
    void shouldConfirmOrderWhenStockSufficient() {
        // Given
        stock.set("mojito", 10);

        // When
        var commande = useCase.execute("user-1", List.of(new PasserCommandeUseCase.OrderLine("mojito", 2)));

        // Then
        assertEquals(CommandeStatus.EN_ATTENTE, commande.getStatus());
        assertEquals(8, stock.availableQuantity("mojito"));
    }

    @Test
    void shouldRejectWhenStockInsufficient() {
        // Given
        stock.set("mojito", 1);

        // When / Then
        assertThrows(DomainExceptions.StockInsuffisantException.class,
                () -> useCase.execute("user-1", List.of(new PasserCommandeUseCase.OrderLine("mojito", 2))));
        assertEquals(1, stock.availableQuantity("mojito"));
    }

    @Test
    void shouldRejectWhenArticleUnknown() {
        // Given empty catalog (no set)

        // When / Then
        assertThrows(DomainExceptions.ArticleInconnuException.class,
                () -> useCase.execute("user-1", List.of(new PasserCommandeUseCase.OrderLine("champagne", 1))));
    }

    // simple in-memory implementation for tests
    static class InMemoryStockPort implements ICommandeRepository {
        private final java.util.Map<String, Integer> map = new java.util.HashMap<>();

        void set(String id, int qty) { map.put(id, qty); }

        @Override
        public boolean exists(String articleId) {
            return map.containsKey(articleId);
        }

        @Override
        public int availableQuantity(String articleId) {
            return map.getOrDefault(articleId, 0);
        }

        @Override
        public void decrement(String articleId, int quantity) {
            map.put(articleId, availableQuantity(articleId) - quantity);
        }
    }
}
