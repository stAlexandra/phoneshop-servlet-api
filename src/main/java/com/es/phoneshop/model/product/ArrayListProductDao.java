package com.es.phoneshop.model.product;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {
    private final List<Product> products = new ArrayList<>();

    private static volatile ArrayListProductDao arrayListProductDao = null;
    private static final Object lock = new Object();
    private ArrayListProductDao() {
    }

    public static ArrayListProductDao getInstance() {
        if (arrayListProductDao == null) {
            synchronized (lock) {
                if (arrayListProductDao == null) {
                    arrayListProductDao = new ArrayListProductDao();
                }
            }
        }
        return arrayListProductDao;
    }

    @Override
    public Product getProduct(Long id) {
        synchronized (products){
            return products.stream()
                    .filter(product -> product.getId().equals(id))
                    .findFirst()
                    .orElseThrow(()->new IllegalArgumentException("No product with id "+ id));
        }

    }

    @Override
    public List<Product> findProducts() {
        synchronized (products){
            return products.stream()
                    .filter(product -> product.getPrice() != null && product.getStock() > 0)
                    .collect(Collectors.toList());
        }

    }

    private boolean productExist(Long id){
        return products.stream().anyMatch(product -> product.getId().equals(id));
    }
    @Override
    public void save(Product product) {
        Long id = product.getId();
        if(id == null){
            throw new IllegalArgumentException("Null product id.");
        }
        synchronized (products){
            if(productExist(id)){
                throw new RuntimeException("Product with id " + id + "already exists.");
            } else {
                products.add(product);
            }
        }
    }

    @Override
    public void delete(Long id) {
        synchronized (products) {
            if(!products.removeIf(product -> product.getId().equals(id))){
                throw new IllegalArgumentException("No product with id "+ id);
            }
        }
    }
}
