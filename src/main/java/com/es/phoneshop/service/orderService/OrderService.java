package com.es.phoneshop.service.orderService;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDetails;

public interface OrderService {
    Order getOrder(String id);
    Order placeOrder(Cart cart, OrderDetails details);
}
