package com.belair.buvette.domain.order;

import java.util.List;
import java.util.Optional;

public interface CommandeRepository {
    void save(Commande c);
    Optional<Commande> findById(String id);
    void updateStatus(String id, CommandeStatus status);
    void saveForFestivalier(String festivalier, Commande c);
    List<Commande> findByFestivalierAndStatus(String festivalier, CommandeStatus status);
}
