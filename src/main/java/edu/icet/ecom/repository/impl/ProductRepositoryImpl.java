package edu.icet.ecom.repository.impl;

import edu.icet.ecom.model.Product;
import edu.icet.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final JdbcTemplate template;

    @Override
    public int saveProduct(Product product) {
        String sql = """
                INSERT INTO products
                (id, name, description, price, stock, category_id, created_at, is_active)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
        return template.update(
                sql,
                product.getId().toString(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().getId().toString(),
                product.getCreatedAt(),
                product.getIsActive()
        );
    }
}
