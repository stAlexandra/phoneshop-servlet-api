package com.es.phoneshop.web;

import com.es.phoneshop.dao.ArrayListPopularProductsDao;
import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exception.NoSuchProductException;
import com.es.phoneshop.exception.NotEnoughStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.cartService.CartService;
import com.es.phoneshop.service.cartService.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QuickOrderPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getInstance();
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/quickOrder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] rawIndexes = request.getParameterValues("rawIndex");
        String[] quantities = request.getParameterValues("quantity");
        String[] codes = request.getParameterValues("code");

        Map<Integer, String> quantityErrors = new HashMap<>();
        Map<Integer, String> codeErrors = new HashMap<>();

        Cart cart = cartService.getCart(request.getSession());

        for(int i = 0; i < rawIndexes.length; i++){
            Product product = null;
            try {
                product = productDao.getProduct(codes[i]);
            } catch (NoSuchProductException e){
                codeErrors.put(i, "Product not found!");
            }

            Integer quantity = null;
            try{
                quantity = Integer.parseUnsignedInt(quantities[i]);
            } catch (NumberFormatException e){
                quantityErrors.put(i, "Not a number!");
            }

            if(quantity != null && product != null){
                try{
                    cartService.addToCart(cart, product, quantity);
                } catch (NotEnoughStockException e){
                    quantityErrors.put(i, "Not enough stock!");
                }
            }
        }

        request.setAttribute("quantityErrors", quantityErrors);
        request.setAttribute("cart", cart);

        if (quantityErrors.isEmpty() && codeErrors.isEmpty()) {
            response.sendRedirect(request.getRequestURI() + "?message=Added successfully");
        } else {
            request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
        }
    }
}
