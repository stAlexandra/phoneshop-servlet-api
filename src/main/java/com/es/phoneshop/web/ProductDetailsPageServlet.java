package com.es.phoneshop.web;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.service.*;
import com.es.phoneshop.model.exception.NoSuchProductException;
import com.es.phoneshop.model.exception.NotEnoughStockException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private CartService cartService;
    private DataLoader dataLoader;
    private RecentlyViewedService recentlyViewedService;

    private static final String QUANTITY_ERROR_ATTRIBUTE = "quantityError";

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = CartServiceImpl.getInstance();
        dataLoader = new DataLoader();
        recentlyViewedService = RecentlyViewedServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Product product = dataLoader.loadProductFromURI(request);
            request.setAttribute("product", product);
            request.setAttribute("viewedProducts", dataLoader.loadRecentlyViewedProductList(request));

            recentlyViewedService.addToList(product, recentlyViewedService.getList(request.getSession()));

            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        } catch (NoSuchProductException | NumberFormatException e){
            response.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = dataLoader.loadProductFromURI(request);
        Cart cart = cartService.getCart(request.getSession());

        request.setAttribute("product", product);
        request.setAttribute("cart", cart);

        Integer quantity = null;
        try {
            quantity = dataLoader.loadQuantity(request, "quantity");
        } catch (NumberFormatException e){
            request.setAttribute(QUANTITY_ERROR_ATTRIBUTE, "Not a number!");
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        }
        if(quantity != null){
            try {
                cartService.addToCart(cart, product, quantity);
                response.sendRedirect(request.getRequestURI()+"?message=success");
            } catch (NotEnoughStockException e){
                request.setAttribute(QUANTITY_ERROR_ATTRIBUTE, "Not enough stock!");
                request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
            }
        }

    }
}
