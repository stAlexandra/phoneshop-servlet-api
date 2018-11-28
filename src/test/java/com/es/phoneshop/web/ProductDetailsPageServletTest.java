package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.Product;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
    private ProductDao productDao;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private Product product;


    @Before
    public void setup() {
        //when(request.getRequestURI()).thenReturn("/dk938");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(productDao.getProduct(anyLong())).thenReturn(product);
    }

    @After
    public void tearDown(){
        productDao.deleteAll();
    }

    @Test
    public void testDoGetValidProduct() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/1");

        servlet.doGet(request, response);

        verify(request).setAttribute("product", product);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetProductNotExist() throws ServletException, IOException {
        //servlet.init();
        when(request.getRequestURI()).thenReturn("/wkd9");

        servlet.doGet(request, response);

        verify(response).sendError(404);
    }
}