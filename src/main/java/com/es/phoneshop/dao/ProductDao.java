package com.es.phoneshop.dao;

import com.es.phoneshop.model.product.Product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);
    Product getProduct(String code);
    List<Product> findProducts(String query, String sortField, boolean sortOrder);
    void save(Product product);
    void delete(Long id);
    void deleteAll();
}
