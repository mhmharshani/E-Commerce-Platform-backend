package edu.icet.ecom.service.impl;

import edu.icet.ecom.model.InventoryLog;
import edu.icet.ecom.model.dto.response.InventoryResponse;
import edu.icet.ecom.repository.InventoryRepository;
import edu.icet.ecom.repository.ProductRepository;
import edu.icet.ecom.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public List<InventoryResponse> getAll() {
        List<InventoryLog> all = inventoryRepository.getAll();
        List<InventoryResponse> logResponseList = new ArrayList<>();

        for(InventoryLog log : all){
            logResponseList.add(
                    InventoryResponse.builder()
                            .id(log.getId())
                            .productId(log.getProductId())
                            .newStock(log.getStockAfter())
                            .action(log.getAction())
                            .createdAt(log.getCreatedAt())
                            .build()
            );
        }
        return logResponseList;
    }

    @Override
    public List<InventoryResponse> getLogsByProductId(UUID productId) {
        List<InventoryLog> all = inventoryRepository.findLogsByProductId(productId);
        List<InventoryResponse> logResponseList = new ArrayList<>();

        for(InventoryLog log : all){
            logResponseList.add(
                    InventoryResponse.builder()
                            .id(log.getId())
                            .productId(log.getProductId())
                            .newStock(log.getStockAfter())
                            .action(log.getAction())
                            .createdAt(log.getCreatedAt())
                            .build()
            );
        }
        return logResponseList;
    }

}
