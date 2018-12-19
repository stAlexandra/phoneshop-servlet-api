package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.cartService.CartService;
import com.es.phoneshop.service.cartService.CartServiceImpl;
import com.es.phoneshop.service.productService.ProductService;
import com.es.phoneshop.service.productService.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDeleteServlet extends HttpServlet {
    private ProductService productService;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        productService = ProductServiceImpl.getInstance();
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = productService.getProduct(request);
        Cart cart = cartService.getCart(request.getSession());

        boolean isDeleted = cartService.deleteItem(cart, product);

        request.setAttribute("cart", cart);
        if(isDeleted) {
            response.sendRedirect(request.getContextPath() + "/cart?message=Deleted successfully");
        } else{
            response.sendRedirect(request.getContextPath() + "/cart?deletionError=true");
        }

    }

}
