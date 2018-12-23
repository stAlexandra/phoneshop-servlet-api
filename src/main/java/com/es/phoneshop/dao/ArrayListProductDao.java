package com.es.phoneshop.dao;

import com.es.phoneshop.exception.NoSuchItemException;
import com.es.phoneshop.model.product.Product;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class ArrayListProductDao extends Dao<Long, Product> implements ProductDao {
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
    public List<Product> findProducts(String query, String sortField, boolean sortOrder) {
        synchronized (items) {
            Predicate<Product> isValid = product -> product.getPrice() != null && product.getStock() > 0;

            List<Product> productsList = items.stream()
                    .filter(isValid)
                    .collect(Collectors.toList());

            if (query != null) {
                String[] queryWords = query.split("\\s+");

                productsList = productsList.stream().collect(Collectors.toMap(UnaryOperator.identity(), product ->
                        Arrays.stream(queryWords).filter(word -> product.getDescription().contains(word)).count()
                )).entrySet().stream()
                        .filter(entry -> entry.getValue() > 0)
                        .sorted(Comparator.comparing(Map.Entry<Product, Long>::getValue).reversed())
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
            }

            if (sortField != null) {
                sort(productsList, sortField, sortOrder);
            }

            return productsList;
        }
    }

    private void sort(List<Product> products, String field, boolean order) {
        if (field != null) {
            if ("description".equals(field)) {
                products.sort(Comparator.comparing(Product::getDescription));
            } else if ("price".equals(field)) {
                products.sort(Comparator.comparing(Product::getPrice));
            }
            if (!order) Collections.reverse(products);
        }
    }

    @Override
    public void delete(Long id) {
        synchronized (items) {
            if (!items.removeIf(product -> product.getId().equals(id))) {
                throw new NoSuchItemException(id.toString());
            }
        }
    }

    @Override
    public void deleteAll() {
        synchronized (items) {
            items.clear();
        }
    }
}
