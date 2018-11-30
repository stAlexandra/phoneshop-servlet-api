package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.exception.NotEnoughStockException;

import java.util.Optional;

public class CartServiceImpl implements CartService {
    private CartServiceImpl(){}
    private static volatile CartServiceImpl instance = null;
    private static final Object lock = new Object();
    public static CartServiceImpl getInstance(){
        if(instance == null){
            synchronized (lock){
                if(instance == null){
                    instance = new CartServiceImpl();
                }
            }
        }
        return instance;
    }

    private Cart cart = new Cart();

    @Override
    public Cart getCart(){
        return cart;
    }

    @Override
    public void addToCart(Product product, Integer quantity) throws NotEnoughStockException{
        int currentStock = product.getStock();
        if(currentStock < quantity) throw new NotEnoughStockException();

        Optional<CartItem> optCartItem = cart.getCartItems().stream().filter(cartItem -> product.getId().equals(cartItem.getProduct().getId())).findAny();
        if(optCartItem.isPresent()){
            CartItem cartItem = optCartItem.get();
            if(currentStock < cartItem.getQuantity() + quantity){
                throw new NotEnoughStockException();
            }
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cart.getCartItems().add(new CartItem(product, quantity));
        }
    }
}
