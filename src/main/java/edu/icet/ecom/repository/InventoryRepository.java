package edu.icet.ecom.repository;

import edu.icet.ecom.model.InventoryLog;

import java.util.List;
import java.util.UUID;

public interface InventoryRepository {

    int save(InventoryLog log);

    List<InventoryLog> getAll();

    List<InventoryLog> findLogsByProductId(UUID productId);

}
