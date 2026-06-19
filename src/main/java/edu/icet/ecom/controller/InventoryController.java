package edu.icet.ecom.controller;

import edu.icet.ecom.model.dto.response.InventoryResponse;
import edu.icet.ecom.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("logs")
    public List<InventoryResponse> getAllInventoryLogs() {
        return inventoryService.getAll();
    }

    @GetMapping ("/{id}/logs")
    public List<InventoryResponse> getProductInventoryLogs(@PathVariable("id") UUID productId) {
        return inventoryService.getLogsByProductId(productId);
    }

}
