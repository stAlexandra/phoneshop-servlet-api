package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.exception.NotEnoughStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.cartService.CartService;
import com.es.phoneshop.service.productService.ProductServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    @InjectMocks
    private CartPageServlet servlet;

    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;
    @Mock
    private ProductServiceImpl productService;
    @Mock
    private Product product;

    @Before
    public void setUp() {
        when(productService.getProduct(anyString())).thenReturn(product);
       // when(cartService.getItemQuantity(anyString())).thenReturn(1);
        when(cartService.getCart(any())).thenReturn(cart);

        String[] nums = new String[]{"1", "2", "3"};

        when(request.getParameterValues("productId")).thenReturn(nums);
        when(request.getParameterValues("quantity")).thenReturn(nums);

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        servlet.doPost(request, response);
        verify(cartService, times(3)).updateCart(eq(cart), any(Product.class), anyInt());
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostQuantityError() throws ServletException, IOException{
        when(request.getParameterValues("quantity")).thenReturn(new String[]{"a", "b", "c"});

        servlet.doPost(request, response);

        verify(cartService, never()).updateCart(eq(cart), any(Product.class), anyInt());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostNotEnoughStock() throws ServletException, IOException{
        doThrow(NotEnoughStockException.class).when(cartService).updateCart(any(), any(), anyInt());

        servlet.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
    }
}