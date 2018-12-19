package com.es.phoneshop.web;

import com.es.phoneshop.model.order.Order;
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
public class OrderOverviewPageServletTest {
    @InjectMocks
    private OrderOverviewPageServlet servlet;

    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpServletRequest request;
    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private OrderService orderService;
    @Mock
    private Order order;

    @Before
    public void setUp() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(orderService.getOrder(any())).thenReturn(order);
    }

    @Test
    public void testDoGet() throws IOException, ServletException {
        servlet.doGet(request, response);

        verify(request).setAttribute(eq("order"), any());
        verify(requestDispatcher).forward(request, response);
    }
}