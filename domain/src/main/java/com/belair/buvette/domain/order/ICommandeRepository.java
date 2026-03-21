package com.belair.buvette.domain.order;

public interface ICommandeRepository {
    boolean exists(String articleId);
    int availableQuantity(String articleId);
    void decrement(String articleId, int quantity);
}
