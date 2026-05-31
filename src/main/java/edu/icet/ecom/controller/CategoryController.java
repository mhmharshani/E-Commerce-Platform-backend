package edu.icet.ecom.controller;

import edu.icet.ecom.model.dto.request.CreateCategoryRequest;
import edu.icet.ecom.model.dto.response.CategoryResponse;
import edu.icet.ecom.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public CategoryResponse createCategory(@RequestBody CreateCategoryRequest request){
        return categoryService.createCategory(request);
    }
}
