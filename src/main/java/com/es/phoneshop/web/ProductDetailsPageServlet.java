package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.CartServiceImpl;
import com.es.phoneshop.model.product.exception.NoSuchProductException;
import com.es.phoneshop.model.product.exception.NotEnoughStockException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {
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
        try {
            Product product = loadProduct(request);
            request.setAttribute("product", product);
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        } catch (NoSuchProductException | NumberFormatException e){
            response.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = loadProduct(request);
        HttpSession httpSession = request.getSession();
        request.setAttribute("product", product);
        request.setAttribute("cart", cartService.getCart(httpSession).getCartItems());

        Integer quantity = null;
        try {
            String quantityString = request.getParameter("quantity");
            quantity = Integer.parseUnsignedInt(quantityString);
        } catch (NumberFormatException e){
            request.setAttribute("quantityError", "Not a number!");
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        }
        if(quantity != null){
            try {
                cartService.addToCart(product, quantity);
                response.sendRedirect(request.getRequestURI()+"?message=success");
            } catch (NotEnoughStockException e){
                request.setAttribute("quantityError", "Not enough stock!");
                request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
            }
        }

    }

    private Product loadProduct(HttpServletRequest request) throws NumberFormatException, NoSuchProductException {
        String uri = request.getRequestURI();
        int lastSlashIndex = uri.lastIndexOf("/");
        String productId = uri.substring(lastSlashIndex + 1);
        long id = Long.parseLong(productId);
        return productDao.getProduct(id);
    }
}
