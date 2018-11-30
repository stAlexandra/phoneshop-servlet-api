package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.exception.NoSuchProductException;

import java.util.*;
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
                    .orElseThrow(()->new NoSuchProductException("No product with id "+ id));
        }
    }


    @Override
    public List<Product> findProducts(String query, String sortField, String sortOrder) {
        synchronized (products){
            List<Product> result = products.stream()
                    .filter(product -> product.getPrice() != null && product.getStock() > 0)
                    .collect(Collectors.toList());

            if(query != null) {
                String[] queryWords = query.split(" ");
                result = result.stream()
                        .filter(product -> Arrays.stream(queryWords).anyMatch(word -> product.getDescription().contains(word)))
                        .sorted((product1, product2) -> {
                            Long numMatches1 = Arrays.stream(queryWords).filter(word -> product1.getDescription().contains(word)).count();
                            Long numMatches2 = Arrays.stream(queryWords).filter(word -> product2.getDescription().contains(word)).count();

                            return numMatches2.compareTo(numMatches1);
                        })
                        .collect(Collectors.toList());
            }

            if(sortField != null && sortOrder != null){
                sort(result, sortField, sortOrder);
            }

            return result;
        }
    }

    private void sort(List<Product> products, String field, String order){
        if(field != null && order != null){
            if(field.equals("description")){
                products.sort(Comparator.comparing(Product::getDescription));
            } else if(field.equals("price")) {
                products.sort(Comparator.comparing(Product::getPrice));
            }
            if(order.equals("desc")) Collections.reverse(products);
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
        return products.stream().anyMatch(product -> product.getId().equals(id));
    }

    @Override
    public void delete(Long id) {
        synchronized (products) {
            if(!products.removeIf(product -> product.getId().equals(id))){
                throw new IllegalArgumentException("No product with id "+ id);
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
