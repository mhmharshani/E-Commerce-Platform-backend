package edu.icet.ecom.repository.impl;

import edu.icet.ecom.mapper.UserRowMapper;
import edu.icet.ecom.model.Category;
import edu.icet.ecom.model.User;
import edu.icet.ecom.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JdbcTemplate template;

    @Override
    public int saveCategory(Category category) {
        String sql = """
                INSERT INTO categories
                (id, name, description)
                VALUES (?, ?, ?)
                """;
        return template.update(
                sql,
                category.getId().toString(),
                category.getName(),
                category.getDescription()
        );
    }

    @Override
    public Category findCategoryById(String id) {
        Category category = template.queryForObject(""" 
                        SELECT *
                        FROM categories
                        WHERE id = ?""",
                new BeanPropertyRowMapper<>(Category.class),
                id
        );
        return category == null ? null : category;
    }
}
