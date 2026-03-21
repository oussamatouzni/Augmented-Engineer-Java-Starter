package com.belair.buvette.infrastructure.order;

import com.belair.buvette.domain.order.Commande;
import com.belair.buvette.domain.order.CommandeRepository;
import com.belair.buvette.domain.order.CommandeStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaCommandeRepositoryAdapter implements CommandeRepository {

    private final SpringDataCommandeRepository repo;

    public JpaCommandeRepositoryAdapter(SpringDataCommandeRepository repo) {
        this.repo = repo;
    }

    public void save(Commande c) {
        CommandeEntity e = new CommandeEntity(c.getId(), c.getStatus().name(), null);
        // map lines
        c.getLines().forEach(l -> e.addLine(new CommandeLineEntity(l.getArticleId(), l.getQuantity(), e)));
        repo.save(e);
    }

    public Optional<Commande> findById(String id) {
        return repo.findById(id).map(e -> new Commande(e.getId(), e.getLines().stream()
            .map(le -> new com.belair.buvette.domain.order.CommandeLine(le.getArticleId(), le.getQuantity()))
            .toList(), CommandeStatus.valueOf(e.getStatus())));
    }

    public void updateStatus(String id, CommandeStatus status) {
        repo.findById(id).ifPresent(e -> {
            e.setStatus(status.name());
            repo.save(e);
        });
    }

    public void saveForFestivalier(String festivalier, Commande c) {
        CommandeEntity e = new CommandeEntity(c.getId(), c.getStatus().name(), festivalier);
        c.getLines().forEach(l -> e.addLine(new CommandeLineEntity(l.getArticleId(), l.getQuantity(), e)));
        repo.save(e);
    }

    public List<Commande> findByFestivalierAndStatus(String festivalier, CommandeStatus status) {
        return repo.findByFestivalierAndStatus(festivalier, status.name()).stream()
                .map(e -> new Commande(e.getId(), List.of(), CommandeStatus.valueOf(e.getStatus())))
                .collect(Collectors.toList());
    }
}
