package com.es.phoneshop.web;

import com.es.phoneshop.exception.NoSuchItemException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.exception.NotEnoughStockException;
import com.es.phoneshop.service.cartService.CartService;
import com.es.phoneshop.service.cartService.CartServiceImpl;
import com.es.phoneshop.service.productService.ProductService;
import com.es.phoneshop.service.productService.ProductServiceImpl;
import com.es.phoneshop.service.recentlyViewedService.RecentlyViewedService;
import com.es.phoneshop.service.recentlyViewedService.RecentlyViewedServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private CartService cartService;
    private ProductService productService;
    private RecentlyViewedService recentlyViewedService;

    private static final String QUANTITY_ERROR_ATTRIBUTE = "quantityError";

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = CartServiceImpl.getInstance();
        productService = ProductServiceImpl.getInstance();
        recentlyViewedService = RecentlyViewedServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Product product = productService.getProduct(request);
            request.setAttribute("product", product);

            request.setAttribute("viewedProducts", recentlyViewedService.getList(request.getSession()));
            recentlyViewedService.addToList(product, recentlyViewedService.getList(request.getSession()));

            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        } catch (NoSuchItemException | NumberFormatException e){
            response.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = productService.getProduct(request);
        Cart cart = cartService.getCart(request.getSession());

        request.setAttribute("product", product);
        request.setAttribute("cart", cart);

        Integer quantity = null;
        try {
            quantity = cartService.getItemQuantity(request, "quantity");
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
