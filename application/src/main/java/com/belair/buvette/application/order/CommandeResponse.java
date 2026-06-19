package com.belair.buvette.application.order;

public class CommandeResponse {
    private final String commandeId;
    private final String status;
    private final Integer remainingStock;

    public CommandeResponse(String commandeId, String status, Integer remainingStock) {
        this.commandeId = commandeId;
        this.status = status;
        this.remainingStock = remainingStock;
    }

    public String getCommandeId() { return commandeId; }
    public String getStatus() { return status; }
    public Integer getRemainingStock() { return remainingStock; }
}
