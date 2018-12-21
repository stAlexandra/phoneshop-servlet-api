package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.exception.NotEnoughOrderDetailsException;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDetails;
import com.es.phoneshop.service.cartService.CartService;
import com.es.phoneshop.service.cartService.CartServiceImpl;
import com.es.phoneshop.service.orderService.OrderService;
import com.es.phoneshop.service.orderService.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckoutPageServlet extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = CartServiceImpl.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request.getSession()));
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Cart cart = cartService.getCart(request.getSession());
        request.setAttribute("cart", cart);
        try {
            OrderDetails details = getOrderDetails(request);
            Order order = orderService.placeOrder(cart, details);

            response.sendRedirect(request.getContextPath() + "/order/overview/" + order.getId());
            return;
        } catch (NotEnoughOrderDetailsException e){
            request.setAttribute("error", "Missed order details!");
        } catch (NumberFormatException e){
            request.setAttribute("error", "Wrong input!");
        }
        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    private OrderDetails getOrderDetails(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String deliveryAddress = request.getParameter("deliveryAddress");
        String phone = request.getParameter("phone");
        if (firstName.isEmpty() || lastName.isEmpty() || deliveryAddress.isEmpty() || phone.isEmpty()) {
            throw new NotEnoughOrderDetailsException();
        }
        OrderDetails details = new OrderDetails();
        details.setFirstName(firstName);
        details.setLastName(lastName);
        details.setDeliveryAddress(deliveryAddress);
        details.setPhone(phone);
        return details;
    }
}
