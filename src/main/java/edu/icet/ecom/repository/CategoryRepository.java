package edu.icet.ecom.repository;

import edu.icet.ecom.model.Category;

import java.util.List;

public interface CategoryRepository {

    public int saveCategory(Category category);

    public Category findCategoryById(String id);

    public List<Category> getCategories();
}
