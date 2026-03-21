package com.belair.buvette.application.order;

import com.belair.buvette.domain.order.Commande;
import com.belair.buvette.domain.order.PasserCommandeUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class PasserCommandeApplicationService {
    private final InMemoryStockAdapter stockAdapter;

    PasserCommandeApplicationService(InMemoryStockAdapter stockAdapter) {
        this.stockAdapter = stockAdapter;
    }

    public Commande createCommande(String festivalierId, List<PasserCommandeUseCase.OrderLine> lines) {
        PasserCommandeUseCase useCase = new PasserCommandeUseCase(stockAdapter);
        return useCase.execute(festivalierId, lines);
    }

    // helper for tests / bootstrapping
    void setStock(String id, int qty) { stockAdapter.set(id, qty); }
}
