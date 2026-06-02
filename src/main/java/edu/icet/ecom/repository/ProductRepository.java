package edu.icet.ecom.repository;

import edu.icet.ecom.model.Product;

import java.util.List;

public interface ProductRepository {

    public int saveProduct(Product product);

    public List<Product> getProducts();
}
