package edu.icet.ecom.repository.impl;

import edu.icet.ecom.mapper.ProductRowMapper;
import edu.icet.ecom.model.Product;
import edu.icet.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final JdbcTemplate template;

    @Override
    public int saveProduct(Product product) {
        String sql = """
                INSERT INTO products
                (id, name, description, price, stock, category_id, created_at, updated_at, is_active)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
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
                product.getUpdatedAt(),
                product.getIsActive()
        );
    }

    @Override
    public List<Product> getProducts() {
        String sql = """
                SELECT *
                FROM products
                """;
        return template.query(sql, new ProductRowMapper());
    }

    @Override
    public Product findProductById(UUID productId) {
        String sql = """
                SELECT *
                FROM products
                WHERE id = ?""";
        try {
            return template.queryForObject(
                    sql,
                    new ProductRowMapper(),
                    productId.toString()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int updateStock(UUID productId, int quantity) { //Decrease product stock by quantity
        String sql = """
            UPDATE products
            SET stock = stock -?
            WHERE id = ?
            AND stock >= ?
        """;

         return template.update(
                sql,
                quantity,
                productId.toString(),
                quantity
        );
    }

}
