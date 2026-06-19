package edu.icet.ecom.mapper;

import edu.icet.ecom.enums.InventoryAction;
import edu.icet.ecom.model.InventoryLog;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

//Used for Select Queries
public class InventoryLogRowMapper implements RowMapper<InventoryLog> {

    @Override
    public InventoryLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        InventoryLog log = new InventoryLog();
        log.setId(UUID.fromString(rs.getString("id")));
        log.setProductId(UUID.fromString(rs.getString("product_id")));
        log.setQuantityChange(rs.getInt("quantity_change"));
        log.setStockBefore(rs.getInt("stock_before"));
        log.setStockAfter(rs.getInt("stock_after"));
        log.setAction(InventoryAction.valueOf(rs.getString("inventory_action")));
        log.setReferenceId(UUID.fromString(rs.getString("reference_id")));
        log.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        return log;
    }
}
