package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.Product;

public interface CartService {
    Cart getCart();
    void addToCart(Product product, Integer quantity);
}
