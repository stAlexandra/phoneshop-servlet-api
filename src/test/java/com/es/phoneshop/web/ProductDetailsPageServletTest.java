package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.exception.NoSuchProductException;
import com.es.phoneshop.model.exception.NotEnoughStockException;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.DataLoader;
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
    private DataLoader dataLoader;

    private static final String QUANTITY_ERROR_ATTRIBUTE = "quantityError";
    private static final String QUANTITY_PARAMETER = "quantity";

    @Before
    public void setUp() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGetValidProduct() throws ServletException, IOException {
        when(dataLoader.loadProductFromURI(request)).thenReturn(product);

        servlet.doGet(request, response);

        verify(request).setAttribute("product", product);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetProductNotExist() throws ServletException, IOException {
        when(dataLoader.loadProductFromURI(request)).thenThrow(NoSuchProductException.class);

        servlet.doGet(request, response);

        verify(response).sendError(404);
    }

    @Test
    public void testDoGetInvalidProductId() throws ServletException, IOException {
        when(dataLoader.loadProductFromURI(request)).thenThrow(NumberFormatException.class);

        servlet.doGet(request, response);

        verify(response).sendError(404);
    }

    @Test
    public void testDoPostSendRedirectWhenOK() throws ServletException, IOException{
        int quantity = 1;
        when(dataLoader.loadProductFromURI(request)).thenReturn(product);
        when(dataLoader.loadQuantity(request, QUANTITY_PARAMETER)).thenReturn(quantity);

        servlet.doPost(request, response);

        verify(cartService).addToCart(any(), eq(product), eq(quantity));
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostQuantityError() throws ServletException, IOException{
        when(dataLoader.loadQuantity(request, QUANTITY_PARAMETER)).thenThrow(NumberFormatException.class);

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