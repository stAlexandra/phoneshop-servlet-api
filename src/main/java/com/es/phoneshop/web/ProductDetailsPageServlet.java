package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ArrayListProductDao productDao;

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //super.doGet(request, response);
        String uri = request.getRequestURI();
        int lastSlashIndex = uri.lastIndexOf("/");
        String productId = uri.substring(lastSlashIndex + 1);

        long id = Long.parseLong(productId);
        Product current = productDao.getProduct(id);

            request.setAttribute("product", current);
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);

    }
}
