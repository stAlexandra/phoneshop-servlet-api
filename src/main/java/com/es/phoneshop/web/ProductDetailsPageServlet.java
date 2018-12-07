package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.cart.Cart;
import com.es.phoneshop.service.CartService;
import com.es.phoneshop.service.CartServiceImpl;
import com.es.phoneshop.model.product.exception.NoSuchProductException;
import com.es.phoneshop.model.product.exception.NotEnoughStockException;
import com.es.phoneshop.service.DataLoader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
    private ProductDao productDao;
    private CartService cartService;
    private DataLoader dataLoader;

    private static final String QUANTITY_ERROR_ATTRIBUTE = "quantityError";

    @Override
    public void init() throws ServletException {
        super.init();
        productDao = ArrayListProductDao.getInstance();
        cartService = CartServiceImpl.getInstance();
        dataLoader = new DataLoader();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Product product = dataLoader.loadProduct(request, productDao);
            request.setAttribute("product", product);
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        } catch (NoSuchProductException | NumberFormatException e){
            response.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = dataLoader.loadProduct(request, productDao);
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
