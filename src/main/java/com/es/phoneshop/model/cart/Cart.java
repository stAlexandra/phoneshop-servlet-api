package com.es.phoneshop.model.cart;

import java.math.BigDecimal;
import java.util.*;

public class Cart {
    private List<CartItem> items;
    private BigDecimal totalPrice;
    private Currency currency;

    public Cart() {
        items = new ArrayList<>();
        totalPrice = BigDecimal.ZERO;
        currency = Currency.getInstance(Locale.US);
    }

    public Cart(Cart otherCart){
        this();
        if(otherCart != null) {
            for (CartItem item : otherCart.items) {
                this.items.add(new CartItem(item.getProduct(), item.getQuantity()));
            }
            this.totalPrice = otherCart.totalPrice;
            this.currency = otherCart.currency;
        }
    }

    public List<CartItem> getCartItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(items, cart.items) &&
                Objects.equals(getTotalPrice(), cart.getTotalPrice()) &&
                Objects.equals(getCurrency(), cart.getCurrency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, getTotalPrice(), getCurrency());
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}
