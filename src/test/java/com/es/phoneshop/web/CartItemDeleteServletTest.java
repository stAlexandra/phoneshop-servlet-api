package com.es.phoneshop.web;

import com.es.phoneshop.service.cartService.CartService;
import com.es.phoneshop.service.productService.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartItemDeleteServletTest {
    @InjectMocks
    private CartItemDeleteServlet servlet;

    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;

    @Mock
    private CartService cartService;
    @Mock
    private ProductService  productService;

    @Test
    public void testDeleted() throws IOException {
        when(cartService.deleteItem(any(), any())).thenReturn(true);

        servlet.doPost(request, response);

        verify(cartService).deleteItem(any(), any());
        verify(request).setAttribute(eq("cart"), any());
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testNotDeleted() throws IOException {
        when(cartService.deleteItem(any(), any())).thenReturn(false);

        servlet.doPost(request, response);

        verify(cartService).deleteItem(any(), any());
        verify(request).setAttribute(eq("cart"), any());
        verify(response).sendRedirect(anyString());
    }
}