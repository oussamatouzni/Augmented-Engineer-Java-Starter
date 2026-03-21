package com.belair.buvette.domain.order;

public class DomainExceptions {
    public static class ArticleInconnuException extends RuntimeException {
        public ArticleInconnuException(String articleId) { super("ARTICLE_INCONNU: " + articleId); }
    }

    public static class StockInsuffisantException extends RuntimeException {
        public StockInsuffisantException(String articleId) { super("STOCK_INSUFFISANT: " + articleId); }
    }
}
