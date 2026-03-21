package com.belair.buvette.infrastructure.order;

import com.belair.buvette.domain.order.Commande;
import com.belair.buvette.domain.order.Commande;
import com.belair.buvette.domain.order.CommandeStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandeRepositoryInfraTest {

    // Use the production infra implementation backed by H2
    @Test
    void shouldSaveAndRetrieveCommande() {
        // Given a commande with status EN_ATTENTE (lines omitted for test isolation)
        Commande commande = new Commande("cmd-1", List.of(), CommandeStatus.EN_ATTENTE);

        // When we save the commande
        CommandeRepositoryImpl repo = new CommandeRepositoryImpl();
        repo.save(commande);

        // Then it can be retrieved by id with same status
        var found = repo.findById("cmd-1");
        assertTrue(found.isPresent());
        assertEquals(CommandeStatus.EN_ATTENTE, found.get().getStatus());
    }

    @Test
    void shouldUpdateStatusToRefuse() {
        // Given a saved commande in EN_ATTENTE
        Commande commande = new Commande("cmd-2", List.of(), CommandeStatus.EN_ATTENTE);
        CommandeRepositoryImpl repo = new CommandeRepositoryImpl();
        repo.save(commande);

        // When we update its status to REFUSE (using existing enum)
        repo.updateStatus("cmd-2", CommandeStatus.REFUSE);

        // Then retrieving it returns status REFUSE
        var found = repo.findById("cmd-2");
        assertTrue(found.isPresent());
        assertEquals(CommandeStatus.REFUSE, found.get().getStatus());
    }

    @Test
    void shouldFindPendingCommandsForFestivalier() {
        // Given 3 commands for festivalier-42 (2 EN_ATTENTE, 1 REFUSE)
        CommandeRepositoryImpl repo = new CommandeRepositoryImpl();
        Commande c1 = new Commande("a", List.of(), CommandeStatus.EN_ATTENTE);
        Commande c2 = new Commande("b", List.of(), CommandeStatus.EN_ATTENTE);
        Commande c3 = new Commande("c", List.of(), CommandeStatus.REFUSE);
        repo.saveForFestivalier("festivalier-42", c1);
        repo.saveForFestivalier("festivalier-42", c2);
        repo.saveForFestivalier("festivalier-42", c3);

        // When searching for EN_ATTENTE for festivalier-42
        var pending = repo.findByFestivalierAndStatus("festivalier-42", CommandeStatus.EN_ATTENTE);

        // Then we get exactly 2 commandes
        assertEquals(2, pending.size());
    }
}
