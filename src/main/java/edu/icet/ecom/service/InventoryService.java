package edu.icet.ecom.service;

import edu.icet.ecom.model.dto.request.UpdateInventoryRequest;
import edu.icet.ecom.model.dto.response.InventoryResponse;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    List<InventoryResponse> getAll();

    List<InventoryResponse> getLogsByProductId(UUID productId);
}
