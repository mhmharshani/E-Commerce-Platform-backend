package edu.icet.ecom.repository;

import edu.icet.ecom.model.Product;
import java.util.List;
import java.util.UUID;

public interface ProductRepository {

    public int saveProduct(Product product);

    public List<Product> getProducts();

    Product findProductById(UUID productId);
}
