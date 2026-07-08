package edu.icet.ecom.controller;

import edu.icet.ecom.model.Product;
import edu.icet.ecom.model.dto.request.CreateProductRequest;
import edu.icet.ecom.model.dto.response.ProductResponse;
import edu.icet.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ProductResponse createProduct(@RequestBody CreateProductRequest request){
        return productService.createProduct(request);
    }

    @GetMapping("/get-all")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

}
