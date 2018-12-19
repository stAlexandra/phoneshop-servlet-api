package com.es.phoneshop.model.cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

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
        for(CartItem item : otherCart.items){
            this.items.add(new CartItem(item.getProduct(), item.getQuantity()));
        }
        this.totalPrice = otherCart.totalPrice;
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
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}
