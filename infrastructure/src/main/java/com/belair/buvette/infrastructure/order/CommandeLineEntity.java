package com.belair.buvette.infrastructure.order;

import jakarta.persistence.*;

@Entity
@Table(name = "commande_lines")
public class CommandeLineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String articleId;
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private CommandeEntity commande;

    public CommandeLineEntity() {}

    public CommandeLineEntity(String articleId, int quantity, CommandeEntity commande) {
        this.articleId = articleId;
        this.quantity = quantity;
        this.commande = commande;
    }

    public String getArticleId() { return articleId; }
    public int getQuantity() { return quantity; }
}
