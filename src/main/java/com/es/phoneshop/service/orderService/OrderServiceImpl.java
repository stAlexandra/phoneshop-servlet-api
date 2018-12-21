package com.es.phoneshop.service.orderService;

import com.es.phoneshop.dao.ArrayListOrderDao;
import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.exception.NotEnoughOrderDetailsException;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {
    private OrderServiceImpl() {
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

        ArrayListOrderDao.getInstance().save(order);

        return order;
    }

    @Override
    public Order getOrder(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int idIndex = uri.lastIndexOf("/");
        String id = uri.substring(idIndex + 1);

        OrderDao orderDao = ArrayListOrderDao.getInstance();
        return orderDao.get(id);
    }

    @Override
    public OrderDetails getOrderDetails(HttpServletRequest request) {
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
