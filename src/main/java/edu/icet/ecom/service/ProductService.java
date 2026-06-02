package edu.icet.ecom.service;

import edu.icet.ecom.model.Product;
import edu.icet.ecom.model.dto.request.CreateProductRequest;
import edu.icet.ecom.model.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    public ProductResponse createProduct(CreateProductRequest request);

    List<Product> getAllProducts();
}
