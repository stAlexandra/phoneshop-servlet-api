package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.exception.NotEnoughStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.CartServiceImpl;
import com.es.phoneshop.service.DataLoader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CartPageServlet extends HttpServlet {
    CartService cartService;
    ProductDao productDao;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = CartServiceImpl.getInstance();
        productDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request.getSession()));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] quantities = request.getParameterValues("quantity");
        String[] productIds = request.getParameterValues("productId");

        Map<Long, String> quantityErrors = new HashMap<>();

        Cart cart = cartService.getCart(request.getSession());
        for(int i = 0; i < productIds.length; i++){
            Long productId = Long.valueOf(productIds[i]);
            Product product = productDao.getProduct(productId);
            Integer quantity = null;
            try{
                quantity = Integer.parseUnsignedInt(quantities[i]);
            } catch (NumberFormatException e){
                quantityErrors.put(productId, "Not a number!");
            }
            if(quantity != null){
                try{
                    cartService.updateCart(cart, product, quantity);
                } catch (NotEnoughStockException e){
                    quantityErrors.put(productId, "Not enough stock!");
                }
            }
        }

        request.setAttribute("quantityErrors", quantityErrors);

        if (quantityErrors.isEmpty()) {
            response.sendRedirect(request.getRequestURI() + "?message=Updated successfully");
        } else {
            request.setAttribute("cart", cart);
            request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
        }
    }
}
