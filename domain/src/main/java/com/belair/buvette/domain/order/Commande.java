package com.belair.buvette.domain.order;

import java.util.List;

public class Commande {
    private final String id;
    private final java.util.List<com.belair.buvette.domain.order.CommandeLine> lines;
    private CommandeStatus status;

    public Commande(String id, java.util.List<com.belair.buvette.domain.order.CommandeLine> lines, CommandeStatus status) {
        this.id = id;
        this.lines = lines;
        this.status = status;
    }

    public String getId() { return id; }
    public java.util.List<com.belair.buvette.domain.order.CommandeLine> getLines() { return lines; }
    public CommandeStatus getStatus() { return status; }
    public void setStatus(CommandeStatus status) { this.status = status; }
}
