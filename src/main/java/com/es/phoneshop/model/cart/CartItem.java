package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.util.Objects;

public class CartItem {
    private Product product;
    private Integer quantity;

    public CartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem(CartItem other){
        this.product = other.getProduct();
        this.quantity = other.getQuantity();
    }

    public Product getProduct() {
        return product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(getProduct(), cartItem.getProduct()) &&
                Objects.equals(getQuantity(), cartItem.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProduct(), getQuantity());
    }

    @Override
    public String toString() {
        return "{" +
                "product=" + product.getCode() +
                ", quantity=" + quantity +
                '}';
    }
}
