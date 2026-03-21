package com.belair.buvette.infrastructure.order;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commandes")
public class CommandeEntity {

    @Id
    private String id;

    @Column(name = "status")
    private String status;

    @Column(name = "festivalier")
    private String festivalier;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommandeLineEntity> lines = new ArrayList<>();

    public CommandeEntity() {}

    public CommandeEntity(String id, String status, String festivalier) {
        this.id = id;
        this.status = status;
        this.festivalier = festivalier;
    }

    public String getId() { return id; }
    public String getStatus() { return status; }
    public String getFestivalier() { return festivalier; }

    public void setStatus(String status) { this.status = status; }

    public List<CommandeLineEntity> getLines() { return lines; }
    public void addLine(CommandeLineEntity line) { lines.add(line); }
}
