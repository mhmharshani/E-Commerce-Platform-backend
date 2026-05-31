package edu.icet.ecom.service;

import edu.icet.ecom.model.dto.request.CreateProductRequest;
import edu.icet.ecom.model.dto.response.ProductResponse;

public interface ProductService {

    public ProductResponse createProduct(CreateProductRequest request);

}
