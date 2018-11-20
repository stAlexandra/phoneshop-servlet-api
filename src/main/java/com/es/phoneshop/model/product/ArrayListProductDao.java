package com.es.phoneshop.model.product;

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
                    .orElseThrow(()->new IllegalArgumentException("No product with id "+ id));
        }

    }


    @Override
    public List<Product> findProducts(String query, String sortField, String sortOrder) {
        String[] queryWords;
        if(query == null){
            queryWords = null;
        } else {
            queryWords = query.split(" ");
        }

        synchronized (products){
            List<Product> filteredProducts = products.stream()
                    .filter(product -> product.getPrice() != null && product.getStock() > 0)
                    .filter(product -> query == null || Arrays.stream(queryWords).anyMatch(word -> product.getDescription().contains(word)))
                    .collect(Collectors.toList());

            if(sortField != null && sortOrder != null){
                if(sortField.equals("description")){
                    sortByDescription(filteredProducts, sortOrder);
                } else if(sortField.equals("price")) {
                    sortByPrice(filteredProducts, sortOrder);
                }
            }

            filteredProducts = filteredProducts.stream()
                    .sorted((product1, product2) -> {
                        if(queryWords == null) return 0;

                        Long s1 = Arrays.stream(queryWords).filter(word -> product1.getDescription().contains(word)).count();
                        Long s2 = Arrays.stream(queryWords).filter(word -> product2.getDescription().contains(word)).count();

                        return s2.compareTo(s1);
                    })
                    .collect(Collectors.toList());

            return filteredProducts;
        }
    }

    private void sortByDescription(List<Product> products, String order) {
        if (order.equals("asc")) {
            products.sort(Comparator.comparing(Product::getDescription));
        } else {
            products.sort(Comparator.comparing(Product::getDescription).reversed());
        }
    }

    private void sortByPrice(List<Product> products, String order) {
        if(order.equals("asc")) {
            products.sort(Comparator.comparing(Product::getPrice));
        } else {
            products.sort(Comparator.comparing(Product::getPrice).reversed());
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
}
