package com.es.phoneshop.dao;

import com.es.phoneshop.exception.NoSuchProductException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductWithViews;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayListPopularProductsDao {
    private final List<ProductWithViews> productsWithViews = new ArrayList<>();

    private static volatile ArrayListPopularProductsDao instance = null;
    private static final Object lock = new Object();
    private ArrayListPopularProductsDao() {
    }

    public static ArrayListPopularProductsDao getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ArrayListPopularProductsDao();
                }
            }
        }
        return instance;
    }

    public ProductWithViews getProduct(Long id) {
        synchronized (productsWithViews){
            return productsWithViews.stream()
                    .filter(item -> id.equals(item.getId()))
                    .findAny()
                    .orElseThrow(()-> new NoSuchProductException(id));
        }
    }

    public List<ProductWithViews> getProducts() {
        synchronized (productsWithViews){
            Predicate<Product> isValid = product -> product.getPrice() != null && product.getStock() > 0;

            return productsWithViews.stream()
                    .filter(isValid)
                    .collect(Collectors.toList());
        }
    }

    public void saveProduct(ProductWithViews item) {
        Long id = item.getId();
        if(id == null){
            throw new IllegalArgumentException("Null id.");
        }
        synchronized (productsWithViews){
            if(exists(id)){
                throw new RuntimeException("Already exists.");
            } else {
                productsWithViews.add(item);
            }
        }
    }

    private synchronized boolean exists(Long id){
        return productsWithViews.stream().anyMatch(item -> id.equals(item.getId()));
    }

}
