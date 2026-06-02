package edu.icet.ecom.service;

import edu.icet.ecom.model.Category;
import edu.icet.ecom.model.dto.request.CreateCategoryRequest;
import edu.icet.ecom.model.dto.response.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    public CategoryResponse createCategory(CreateCategoryRequest request);

    public Category findCategoryById(String id);

    public List<Category> getAllCategories();
}
