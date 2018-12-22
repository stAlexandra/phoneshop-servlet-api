package com.es.phoneshop.service.productService;

import com.es.phoneshop.exception.NoSuchItemException;
import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface ProductService {
    Product getProduct(Long productId) throws NoSuchItemException;
    Product getProduct(String productId) throws NumberFormatException, NoSuchItemException;
    List<Product> getFilteredProducts(String query, String sortField, boolean sortOrder);
    boolean sortAscending(String sortOrder);
}
