package com.es.phoneshop.model.product;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private List<Product> products = new ArrayList<>();

    @Override
    public Product getProduct(Long id) {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst().orElseThrow(NullPointerException::new);
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public List<Product> findProducts() {
        return products.stream().filter(product -> product.getPrice() != null && product.getStock() > 0).collect(Collectors.toList());
    }

    private boolean hasProductWithSameID(Long id){
        return products.stream().anyMatch(product -> product.getId().equals(id));
    }

    @Override
    public void save(Product product) {
        if(product == null || product.getId() == null){
            throw new NullPointerException();
        }
        if(!products.isEmpty()){
            if(hasProductWithSameID(product.getId())){
                throw new RuntimeException("Duplicated product ID");
            }
        }
        products.add(product);
    }

    @Override
    public void delete(Long id) {
       products.removeIf(product -> product.getId().equals(id));
    }
}
