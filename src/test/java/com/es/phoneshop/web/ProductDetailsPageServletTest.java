package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.exception.NoSuchProductException;
import com.es.phoneshop.exception.NotEnoughStockException;
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
    private static final String QUANTITY_PARAMETER = "quantity";

    @Before
    public void setUp() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGetValidProduct() throws ServletException, IOException {
        when(productService.getProduct(request)).thenReturn(product);

        servlet.doGet(request, response);

        verify(request).setAttribute("product", product);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetProductNotExist() throws ServletException, IOException {
        when(productService.getProduct(request)).thenThrow(NoSuchProductException.class);

        servlet.doGet(request, response);

        verify(response).sendError(404);
    }

    @Test
    public void testDoGetInvalidProductId() throws ServletException, IOException {
        when(productService.getProduct(request)).thenThrow(NumberFormatException.class);

        servlet.doGet(request, response);

        verify(response).sendError(404);
    }

    @Test
    public void testDoPostSendRedirectWhenOK() throws ServletException, IOException{
        int quantity = 1;
        when(productService.getProduct(request)).thenReturn(product);
        when(cartService.getItemQuantity(request, QUANTITY_PARAMETER)).thenReturn(quantity);

        servlet.doPost(request, response);

        verify(cartService).addToCart(any(), eq(product), eq(quantity));
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostQuantityError() throws ServletException, IOException{
        when(cartService.getItemQuantity(request, QUANTITY_PARAMETER)).thenThrow(NumberFormatException.class);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq(QUANTITY_ERROR_ATTRIBUTE), anyString());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostNotEnoughStock() throws ServletException, IOException{
        doThrow(NotEnoughStockException.class).when(cartService).addToCart(any(), any(), anyInt());

        servlet.doPost(request, response);

        verify(request).setAttribute(eq(QUANTITY_ERROR_ATTRIBUTE), anyString());
        verify(requestDispatcher).forward(request, response);
    }
}