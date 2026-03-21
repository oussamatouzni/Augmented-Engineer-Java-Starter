package com.belair.buvette.infrastructure.order;

import com.belair.buvette.domain.order.Commande;
import com.belair.buvette.domain.order.CommandeStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandeRepositoryImpl {

    private final Connection connection;

    public CommandeRepositoryImpl() {
        try {
            // in-memory H2 DB, keep open for duration of JVM
            String url = "jdbc:h2:mem:commande;DB_CLOSE_DELAY=-1";
            connection = DriverManager.getConnection(url);
            initSchema();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initSchema() throws SQLException {
        try (Statement s = connection.createStatement()) {
            s.execute("CREATE TABLE IF NOT EXISTS commandes (id VARCHAR PRIMARY KEY, status VARCHAR, festivalier VARCHAR)");
        }
    }

    public void save(Commande c) {
        try (PreparedStatement ps = connection.prepareStatement(
                "MERGE INTO commandes (id, status, festivalier) KEY(id) VALUES (?, ?, ?)")) {
            ps.setString(1, c.getId());
            ps.setString(2, c.getStatus().name());
            ps.setString(3, null);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Commande> findById(String id) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT id, status FROM commandes WHERE id = ?")) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String status = rs.getString("status");
                    Commande c = new Commande(id, List.of(), CommandeStatus.valueOf(status));
                    return Optional.of(c);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStatus(String id, CommandeStatus status) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE commandes SET status = ? WHERE id = ?")) {
            ps.setString(1, status.name());
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveForFestivalier(String festivalier, Commande c) {
        try (PreparedStatement ps = connection.prepareStatement(
                "MERGE INTO commandes (id, status, festivalier) KEY(id) VALUES (?, ?, ?)") ) {
            ps.setString(1, c.getId());
            ps.setString(2, c.getStatus().name());
            ps.setString(3, festivalier);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Commande> findByFestivalierAndStatus(String festivalier, CommandeStatus status) {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT id, status FROM commandes WHERE festivalier = ? AND status = ?")) {
            ps.setString(1, festivalier);
            ps.setString(2, status.name());
            try (ResultSet rs = ps.executeQuery()) {
                List<Commande> result = new ArrayList<>();
                while (rs.next()) {
                    String id = rs.getString("id");
                    result.add(new Commande(id, List.of(), CommandeStatus.valueOf(rs.getString("status"))));
                }
                return result;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
