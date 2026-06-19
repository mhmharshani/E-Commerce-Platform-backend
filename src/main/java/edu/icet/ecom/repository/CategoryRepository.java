package edu.icet.ecom.repository;

import edu.icet.ecom.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository {

    public int saveCategory(Category category);

    public Category findCategoryById(UUID id);

    public List<Category> getCategories();
}
