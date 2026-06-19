package edu.icet.ecom.controller;

import edu.icet.ecom.model.Category;
import edu.icet.ecom.model.dto.request.CreateCategoryRequest;
import edu.icet.ecom.model.dto.response.CategoryResponse;
import edu.icet.ecom.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public CategoryResponse createCategory(@RequestBody CreateCategoryRequest request){
        return categoryService.createCategory(request);
    }

    @GetMapping("/get-all")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

}
