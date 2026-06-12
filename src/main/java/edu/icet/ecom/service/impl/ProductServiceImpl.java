package edu.icet.ecom.service.impl;

import edu.icet.ecom.model.Category;
import edu.icet.ecom.model.Product;
import edu.icet.ecom.model.dto.request.CreateProductRequest;
import edu.icet.ecom.model.dto.response.ProductResponse;
import edu.icet.ecom.repository.ProductRepository;
import edu.icet.ecom.service.CategoryService;
import edu.icet.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        LocalDateTime now = Timestamp.from(Instant.now()).toLocalDateTime();

        Product product = Product.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(categoryService.findCategoryById(request.getCategoryId()))
                .createdAt(now)
                .updatedAt(now)
                .isActive(true)
                .build();
        productRepository.saveProduct(product);

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
            Category category = categoryService.findCategoryById(product.getCategory().getId().toString());
            product.setCategory(category);
        });
        return products;
    }
}
