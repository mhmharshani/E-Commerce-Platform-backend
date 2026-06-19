package edu.icet.ecom.repository.impl;

import edu.icet.ecom.mapper.CartItemsRowMapper;
import edu.icet.ecom.mapper.InventoryLogRowMapper;
import edu.icet.ecom.model.CartItems;
import edu.icet.ecom.model.InventoryLog;
import edu.icet.ecom.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepository {

    private final JdbcTemplate template;

    public int save(InventoryLog log) {
        String sql = """
                INSERT INTO inventory_logs
                (id, product_id, quantity_change, stock_before, stock_after, inventory_action, reference_id, created_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        return template.update(
                sql,
                log.getId().toString(),
                log.getProductId().toString(),
                log.getQuantityChange(),
                log.getStockBefore(),
                log.getStockAfter(),
                log.getAction().name(),
                log.getReferenceId().toString(),
                log.getCreatedAt()
        );
    }

    @Override
    public List<InventoryLog> getAll() {
        String sql = """
            SELECT *
            FROM inventory_logs
            """;
        return template.query(
                sql,
                new InventoryLogRowMapper()
        );
    }

    @Override
    public List<InventoryLog> findLogsByProductId(UUID productId) {
        String sql = """
            SELECT *
            FROM inventory_logs
            WHERE product_id = ?
            """;
        return template.query(
                sql,
                new InventoryLogRowMapper(),
                productId.toString()
        );
    }
}
