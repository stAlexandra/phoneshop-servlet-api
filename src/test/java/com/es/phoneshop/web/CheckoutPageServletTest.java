package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.service.cartService.CartService;
import com.es.phoneshop.service.orderService.OrderService;
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
public class CheckoutPageServletTest {
    @InjectMocks
    private CheckoutPageServlet servlet;

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
    private OrderService orderService;
    @Mock
    private Order order;

    @Before
    public void setUp() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getParameter(anyString())).thenReturn("parameter");
        when(orderService.placeOrder(any(), any())).thenReturn(order);
        when(cartService.getCart(any())).thenReturn(cart);
        when(order.getId()).thenReturn("12345");
    }

    @Test
    public void testDoGet() throws IOException, ServletException {
        servlet.doGet(request, response);

        verify(request).setAttribute(eq("cart"), any());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws IOException, ServletException{
        servlet.doPost(request, response);
        verify(orderService).placeOrder(any(), any());
        verify(response).sendRedirect(any());
    }
}