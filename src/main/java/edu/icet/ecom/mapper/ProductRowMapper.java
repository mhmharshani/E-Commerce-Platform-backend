package edu.icet.ecom.mapper;

import edu.icet.ecom.model.Category;
import edu.icet.ecom.model.Product;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

//Used for Select Queries
public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product product = new Product();
        UUID productId = UUID.fromString(rs.getString("id"));

        product.setId(productId);
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getDouble("price"));
        product.setStock(rs.getInt("stock"));
        UUID categoryId = UUID.fromString(rs.getString("category_id"));
        Category category = new Category();
        product.setCategory(category);
        category.setId(categoryId);
        product.setCreatedAt(
                rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null
        );
        product.setUpdatedAt(
                rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null
        );
        product.setIsActive(rs.getBoolean("is_active"));

        return product;
    }
}
