package com.belair.buvette.domain.order;

public class CommandeLine {
    private final String articleId;
    private final int quantity;

    public CommandeLine(String articleId, int quantity) {
        this.articleId = articleId;
        this.quantity = quantity;
    }

    public String getArticleId() { return articleId; }
    public int getQuantity() { return quantity; }
}
