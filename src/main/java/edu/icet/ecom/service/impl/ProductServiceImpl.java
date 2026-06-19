package edu.icet.ecom.service.impl;

import edu.icet.ecom.enums.InventoryAction;
import edu.icet.ecom.model.Category;
import edu.icet.ecom.model.InventoryLog;
import edu.icet.ecom.model.Product;
import edu.icet.ecom.model.dto.request.CreateProductRequest;
import edu.icet.ecom.model.dto.response.ProductResponse;
import edu.icet.ecom.repository.CategoryRepository;
import edu.icet.ecom.repository.InventoryRepository;
import edu.icet.ecom.repository.ProductRepository;
import edu.icet.ecom.service.CategoryService;
import edu.icet.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        LocalDateTime now = Timestamp.from(Instant.now()).toLocalDateTime();

        Category category = categoryRepository.findCategoryById(request.getCategoryId());

        Product product = Product.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(category)
                .createdAt(now)
                .updatedAt(now)
                .isActive(true)
                .build();
        productRepository.saveProduct(product);

        //Create inventory log
        int logCreated = inventoryRepository.save(
                InventoryLog.builder()
                        .id(UUID.randomUUID())
                        .productId(product.getId())
                        .quantityChange(request.getStock())
                        .stockBefore(0)
                        .stockAfter(request.getStock()) //Need to consider place of calculation later due to multithreading
                        .action(InventoryAction.STOCK_IN)
                        .referenceId(UUID.randomUUID())
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        if(logCreated <= 0) log.error("Inventory Log is not inserted to DB");

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .categoryName(product.getCategory().getName())
                .build();
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.getProducts();
        products.forEach(product -> {
            Category category = categoryRepository.findCategoryById(product.getCategory().getId());
            product.setCategory(category);
        });
        return products;
    }
}
