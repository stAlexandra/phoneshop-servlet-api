package com.es.phoneshop.service.cartService;

import com.es.phoneshop.model.exception.NotEnoughStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.cart.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface CartService {
    Cart getCart(HttpSession session);
    void addToCart(Cart cart, Product product, Integer quantity) throws NotEnoughStockException;
    void updateCart(Cart cart, Product product, Integer quantity) throws NotEnoughStockException;
    boolean deleteItem(Cart cart, Product product);
    Integer getItemQuantity(HttpServletRequest request, String quantityParameter) throws NumberFormatException;
    void recalculateCart(Cart cart);
}
