package com.es.phoneshop.web;

import com.es.phoneshop.exception.NoSuchItemException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.cartService.CartService;
import com.es.phoneshop.service.productService.ProductServiceImpl;
import com.es.phoneshop.service.recentlyViewedService.RecentlyViewedService;
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
public class ProductDetailsPageServletTest {
    @InjectMocks
    private static ProductDetailsPageServlet servlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private Product product;
    @Mock
    private CartService cartService;
    @Mock
    private ProductServiceImpl productService;
    @Mock
    private RecentlyViewedService recentlyViewedService;

    private static final String QUANTITY_ERROR_ATTRIBUTE = "quantityError";

    @Before
    public void setUp() {
        when(request.getRequestURI()).thenReturn("/1");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(productService.getProduct(anyLong())).thenReturn(product);
    }

    @Test
    public void testDoGetValidProduct() throws ServletException, IOException {


        servlet.doGet(request, response);

        verify(request).setAttribute("product", product);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetProductNotExist() throws ServletException, IOException {
        when(productService.getProduct(anyLong())).thenThrow(NoSuchItemException.class);

        servlet.doGet(request, response);

        verify(response).sendError(404);
    }

    @Test
    public void testDoGetInvalidProductId() throws ServletException, IOException {
       // when(productService.getProduct(request)).thenThrow(NumberFormatException.class);
        when(request.getRequestURI()).thenReturn("uri"); //NumberFormatException will be thrown

        servlet.doGet(request, response);

        verify(response).sendError(404);
    }

    @Test
    public void testDoPostSendRedirectWhenOK() throws ServletException, IOException{
        //int quantity = 1;
        when(request.getParameter("quantity")).thenReturn("1");

        servlet.doPost(request, response);

        verify(cartService).addToCart(any(), eq(product), eq(1));
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostQuantityError() throws ServletException, IOException{
        when(request.getParameter("quantity")).thenReturn("abc"); //NumberFormatException will be thrown

        servlet.doPost(request, response);

        verify(request).setAttribute(eq(QUANTITY_ERROR_ATTRIBUTE), anyString());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostNotEnoughStock() throws ServletException, IOException{
        servlet.doPost(request, response);

        verify(request).setAttribute(eq(QUANTITY_ERROR_ATTRIBUTE), anyString());
        verify(requestDispatcher).forward(request, response);
    }
}