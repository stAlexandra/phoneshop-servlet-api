package com.es.phoneshop.service.orderService;

import com.es.phoneshop.dao.ArrayListOrderDao;
import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDetails;

import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;

    private OrderServiceImpl() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    private static volatile OrderServiceImpl instance = null;
    private static final Object lock = new Object();

    public static OrderServiceImpl getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new OrderServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Order placeOrder(Cart cart, OrderDetails details) {
        Order order = new Order();
        order.setCart(new Cart(cart));
        order.setDetails(details);
        order.setId(UUID.randomUUID().toString());

        orderDao.save(order);

        return order;
    }

    @Override
    public Order getOrder(String id) {
        return orderDao.get(id);
    }
}
