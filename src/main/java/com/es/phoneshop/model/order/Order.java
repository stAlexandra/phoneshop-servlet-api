package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;

public class Order {
    private String secureId;
    private Cart cart;
    private OrderDetails details;

    public String getSecureId() {
        return secureId;
    }

    public void setSecureId(String secureId) {
        this.secureId = secureId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public OrderDetails getDetails() {
        return details;
    }

    public void setDetails(OrderDetails details) {
        this.details = details;
    }
}
