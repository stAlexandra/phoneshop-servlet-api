package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;

    private ArrayListProductDao productDao;

    private static ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
        List<Product> productList = new ArrayList<>();
        Currency usd = Currency.getInstance("USD");
        productList.add(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https"));
        productList.add(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 2, "https"));
        productList.forEach(product -> productDao.save(product));

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @After
    public void tearDown(){
        productDao.deleteAll();
    }

    @Test
    public void testDoGetValidProduct() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/1");

        servlet.init();
        servlet.doGet(request, response);

        verify(request).setAttribute(eq("product"), any());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetProductNotExist() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/883994");

        servlet.init();
        servlet.doGet(request, response);

        verify(request).getRequestDispatcher("/WEB-INF/pages/error.jsp");
        verify(requestDispatcher).forward(request, response);
    }
}