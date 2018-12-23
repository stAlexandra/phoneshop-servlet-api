package com.es.phoneshop.web;

import com.es.phoneshop.service.cartService.CartService;
import com.es.phoneshop.service.cartService.CartServiceImpl;
import com.es.phoneshop.service.productService.ProductService;
import com.es.phoneshop.service.productService.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ProductService productService;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = ProductServiceImpl.getInstance();
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String sortField = request.getParameter("sort");
        String sortOrderString = request.getParameter("order");

        request.setAttribute("products", productService.getFilteredProducts(query, sortField, productService.sortAscending(sortOrderString)));
        request.setAttribute("cart", cartService.getCart(request.getSession()));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
