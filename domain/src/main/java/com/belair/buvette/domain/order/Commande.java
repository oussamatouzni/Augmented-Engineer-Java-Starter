package com.belair.buvette.domain.order;

import java.util.List;

public class Commande {
    private final String id;
    private final List<CommandeLine> lines;
    private CommandeStatus status;

    public Commande(String id, List<CommandeLine> lines, CommandeStatus status) {
        this.id = id;
        this.lines = lines;
        this.status = status;
    }

    public String getId() { return id; }
    public List<CommandeLine> getLines() { return lines; }
    public CommandeStatus getStatus() { return status; }
    public void setStatus(CommandeStatus status) { this.status = status; }
}

class CommandeLine {
    private final String articleId;
    private final int quantity;

    public CommandeLine(String articleId, int quantity) {
        this.articleId = articleId;
        this.quantity = quantity;
    }

    public String getArticleId() { return articleId; }
    public int getQuantity() { return quantity; }
}
