package com.es.phoneshop.service.cartService;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.exception.NotEnoughStockException;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;

public class CartServiceImpl implements CartService {
    private CartServiceImpl() {
    }

    private static volatile CartServiceImpl instance = null;
    private static final Object lock = new Object();

    public static CartServiceImpl getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new CartServiceImpl();
                }
            }
        }
        return instance;
    }

    public static final String CART_ATTRIBUTE = "cart";

    @Override
    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_ATTRIBUTE, cart);
        }
        return cart;
    }

    @Override
    public void addToCart(Cart cart, Product product, Integer quantity) throws NotEnoughStockException {
        int currentStock = product.getStock();
        if (currentStock < quantity) throw new NotEnoughStockException(quantity);

        Optional<CartItem> optCartItem = cart.getCartItems().stream().filter(cartItem -> product.getId().equals(cartItem.getProduct().getId())).findAny();
        if (optCartItem.isPresent()) {
            CartItem cartItem = optCartItem.get();
            if (currentStock < cartItem.getQuantity() + quantity) {
                throw new NotEnoughStockException(quantity);
            }
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cart.getCartItems().add(new CartItem(product, quantity));
        }

        recalculateCart(cart);
    }

    @Override
    public void updateCart(Cart cart, Product product, Integer quantity) throws NotEnoughStockException {
        int currentStock = product.getStock();
        if (currentStock < quantity) throw new NotEnoughStockException(quantity);

        Optional<CartItem> optCartItem = cart.getCartItems().stream().filter(cartItem -> product.getId().equals(cartItem.getProduct().getId())).findAny();
        optCartItem.ifPresent(cartItem -> cartItem.setQuantity(quantity));

        recalculateCart(cart);
    }

    @Override
    public boolean deleteItem(Cart cart, Product product) {
        boolean deleted = cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(product.getId()));
        recalculateCart(cart);
        return deleted;
    }

    @Override
    public void recalculateCart(Cart cart){
        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        cart.setTotalPrice(totalPrice);
    }
}
