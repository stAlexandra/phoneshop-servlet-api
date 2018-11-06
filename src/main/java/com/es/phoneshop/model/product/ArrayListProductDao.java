package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    List<Product> products = new ArrayList<>();

    @Override
    public Product getProduct(Long id) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public List<Product> findProducts() {
        //throw new RuntimeException("Not implemented");
        return products.stream().filter(product -> product.getPrice() != null && product.getStock() > 0).collect(Collectors.toList());
    }

    @Override
    public void save(Product product) {
        //throw new RuntimeException("Not implemented");
        products.add(product);
    }

    @Override
    public void delete(Long id) {
        throw new RuntimeException("Not implemented");
    }
}
