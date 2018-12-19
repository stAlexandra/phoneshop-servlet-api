package com.es.phoneshop.dao;

import com.es.phoneshop.exception.NoSuchProductException;
import com.es.phoneshop.model.product.Product;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
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
                    .findAny()
                    .orElseThrow(()->new NoSuchProductException(id));
        }
    }


    @Override
    public List<Product> findProducts(String query, String sortField, boolean sortOrder) {
        synchronized (products){
            Predicate<Product> isValid = product -> product.getPrice() != null && product.getStock() > 0;

            List<Product> productsList = products.stream()
                    .filter(isValid)
                    .collect(Collectors.toList());

            if(query != null) {
                String[] queryWords = query.split("\\s+");

                productsList = productsList.stream().collect(Collectors.toMap(UnaryOperator.identity(), product ->
                    Arrays.stream(queryWords).filter(word -> product.getDescription().contains(word)).count()
                )).entrySet().stream()
                        .filter(entry -> entry.getValue() > 0)
                        .sorted(Comparator.comparing(Map.Entry<Product, Long>::getValue).reversed())
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
            }

            if(sortField != null){
                sort(productsList, sortField, sortOrder);
            }

            return productsList;
        }
    }

    private void sort(List<Product> products, String field, boolean order){
        if(field != null){
            if("description".equals(field)){
                products.sort(Comparator.comparing(Product::getDescription));
            } else if("price".equals(field)) {
                products.sort(Comparator.comparing(Product::getPrice));
            }
            if(!order) Collections.reverse(products);
        }
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

    private boolean productExist(Long id){
        return products.stream().anyMatch(product -> id.equals(product.getId()));
    }

    @Override
    public void delete(Long id) {
        synchronized (products) {
            if(!products.removeIf(product -> product.getId().equals(id))){
                throw new NoSuchProductException(id);
            }
        }
    }

    @Override
    public void deleteAll(){
        synchronized (products){
            products.clear();
        }
    }
}
