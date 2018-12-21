package com.es.phoneshop.model.order;

import com.es.phoneshop.model.Identifiable;
import com.es.phoneshop.model.cart.Cart;

public class Order implements Identifiable<String> {
    private String secureId;
    private Cart cart;
    private OrderDetails details;

    @Override
    public String getId() {
        return secureId;
    }

    @Override
    public void setId(String secureId) {
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
