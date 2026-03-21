package com.belair.buvette.domain.order;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PasserCommandeUseCase {

    private final ICommandeRepository stockPort;

    public PasserCommandeUseCase(ICommandeRepository stockPort) {
        this.stockPort = stockPort;
    }

    public Commande execute(String festivalierId, List<OrderLine> requestedLines) {
        // validate festivalier
        if (festivalierId == null || festivalierId.isEmpty()) {
            throw new IllegalArgumentException("festivalierId required");
        }

        // check each line
        for (OrderLine line : requestedLines) {
            String articleId = line.articleId();
            int qty = line.quantity();
            if (!stockPort.exists(articleId)) {
                throw new DomainExceptions.ArticleInconnuException(articleId);
            }
            int available = stockPort.availableQuantity(articleId);
            if (available < qty) {
                throw new DomainExceptions.StockInsuffisantException(articleId);
            }
        }

        // all good — decrement stock
        for (OrderLine line : requestedLines) {
            stockPort.decrement(line.articleId(), line.quantity());
        }

        List<CommandeLine> lines = requestedLines.stream()
                .map(l -> new CommandeLine(l.articleId(), l.quantity()))
                .collect(Collectors.toList());

        Commande commande = new Commande(UUID.randomUUID().toString(), lines, CommandeStatus.EN_ATTENTE);
        return commande;
    }

    public static record OrderLine(String articleId, int quantity) {}
}
