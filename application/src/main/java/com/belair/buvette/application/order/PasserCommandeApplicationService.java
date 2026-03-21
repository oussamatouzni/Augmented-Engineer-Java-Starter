package com.belair.buvette.application.order;

import com.belair.buvette.domain.order.Commande;
import com.belair.buvette.domain.order.CommandeRepository;
import com.belair.buvette.domain.order.ICommandeRepository;
import com.belair.buvette.domain.order.PasserCommandeUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class PasserCommandeApplicationService {
    private final ICommandeRepository stockAdapter;
    private final CommandeRepository commandeRepository;

    PasserCommandeApplicationService(InMemoryStockAdapter stockAdapter, CommandeRepository commandeRepository) {
        this.stockAdapter = stockAdapter;
        this.commandeRepository = commandeRepository;
    }

    public Commande createCommande(String festivalierId, List<PasserCommandeUseCase.OrderLine> lines) {
        PasserCommandeUseCase useCase = new PasserCommandeUseCase(stockAdapter);
        Commande c = useCase.execute(festivalierId, lines);
        commandeRepository.save(c);
        return c;
    }

    // helper for tests / bootstrapping
    void setStock(String id, int qty) { stockAdapter.set(id, qty); }
}
