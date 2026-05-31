package edu.icet.ecom.repository;

import edu.icet.ecom.model.Category;

public interface CategoryRepository {

    public int saveCategory(Category category);

    public Category findCategoryById(String id);

}
