package com.es.phoneshop.service.orderService;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDetails;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {
    Order getOrder(HttpServletRequest request);
    Order placeOrder(Cart cart, OrderDetails details);
    OrderDetails getOrderDetails(HttpServletRequest request);
}
