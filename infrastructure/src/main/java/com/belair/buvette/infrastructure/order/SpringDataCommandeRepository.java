package com.belair.buvette.infrastructure.order;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpringDataCommandeRepository extends JpaRepository<CommandeEntity, String> {
    List<CommandeEntity> findByFestivalierAndStatus(String festivalier, String status);
}
