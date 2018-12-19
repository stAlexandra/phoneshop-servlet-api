package com.es.phoneshop.service.productService;

import com.es.phoneshop.exception.NoSuchProductException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductService {
    Product getProduct(Long productId) throws NoSuchProductException;
    Product getProduct(String productId) throws NumberFormatException, NoSuchProductException;
    Product getProduct(HttpServletRequest request) throws NumberFormatException, NoSuchProductException;
    List<Product> getFilteredProducts(String query, String sortField, boolean sortOrder);
}
