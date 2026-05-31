package edu.icet.ecom.service.impl;

import edu.icet.ecom.model.Category;
import edu.icet.ecom.model.dto.request.CreateCategoryRequest;
import edu.icet.ecom.model.dto.response.CategoryResponse;
import edu.icet.ecom.repository.CategoryRepository;
import edu.icet.ecom.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = new Category(
                UUID.randomUUID(),
                request.getName(),
                request.getDescription()
        );
        int result = categoryRepository.saveCategory(category);
        if(result>0){
            return new CategoryResponse(
                 category.getId(),
                 category.getName()
            );
        }
        return null;
    }

    @Override
    public Category findCategoryById(String id) {
        return categoryRepository.findCategoryById(id);
    }
}
