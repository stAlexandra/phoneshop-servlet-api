package com.es.phoneshop.web;

import com.es.phoneshop.service.productService.ProductService;
import com.es.phoneshop.service.productService.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = ProductServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String sortField = request.getParameter("sort");
        String sortOrderString = request.getParameter("order");

        boolean sortOrder = "asc".equals(sortOrderString);

        request.setAttribute("products", productService.getFilteredProducts(query, sortField, sortOrder));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
