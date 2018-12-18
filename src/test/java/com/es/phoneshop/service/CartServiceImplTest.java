package com.es.phoneshop.service;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.exception.NotEnoughStockException;
import com.es.phoneshop.service.cartService.CartServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceImplTest {
    private CartServiceImpl cartService;

    private Cart cart = new Cart();

    @Mock
    private HttpSession session;
    @Mock
    private CartItem cartItem;
    @Mock
    private Product product;

    @Before
    public void setUp(){
        cartService = CartServiceImpl.getInstance();

        when(cartItem.getProduct()).thenReturn(product);

        when(product.getStock()).thenReturn(1000);
        when(product.getId()).thenReturn(1L);
    }

    @Test
    public void testAddToCart(){
        cartService.addToCart(cart, product, 1);
        assertEquals(1, cart.getCartItems().size());
    }

    @Test
    public void testGetNewCart(){
        Cart cart = cartService.getCart(session);
        verify(session).setAttribute(eq(CartServiceImpl.CART_ATTRIBUTE), any(Cart.class));
        assertNotNull(cart);
    }

    @Test
    public void testGetExistingCart(){
        when(session.getAttribute(CartServiceImpl.CART_ATTRIBUTE)).thenReturn(cart);

        Cart receivedCart = cartService.getCart(session);

        verify(session, never()).setAttribute(eq(CartServiceImpl.CART_ATTRIBUTE), any(Cart.class));
        assertEquals(cart, receivedCart);
    }

    @Test
    public void testAddMoreOfExistingProduct(){
        int originalQuantity = 1;
        int addedQuantity = 1;

        when(cartItem.getQuantity()).thenReturn(originalQuantity);
        cart.getCartItems().add(cartItem);

        cartService.addToCart(cart, product, addedQuantity);

        verify(cartItem).setQuantity(originalQuantity + addedQuantity);
    }

    @Test(expected = NotEnoughStockException.class)
    public void testAddMoreThanStock(){
        cartService.addToCart(cart, product, 1001);
    }

    @Test(expected = NotEnoughStockException.class)
    public void testAddMoreThanStockToExistingItem(){
        int originalQuantity = 1000;
        int addedQuantity = 1;

        when(cartItem.getQuantity()).thenReturn(originalQuantity);
        cart.getCartItems().add(cartItem);

        cartService.addToCart(cart, product, addedQuantity);
    }

}
