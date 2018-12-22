package com.es.phoneshop.service.orderService;

import com.es.phoneshop.dao.OrderDao;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderDetails;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

public class OrderServiceImplTest {
    @InjectMocks
    private OrderService service = OrderServiceImpl.getInstance();

    @Mock
    OrderDetails orderDetails;
    @Mock
    private OrderDao orderDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void placeOrder() {
        Cart cart = new Cart();

        Order order = service.placeOrder(cart, orderDetails);

        verify(orderDao).save(any(Order.class));
        assertNotNull(order);
    }

    @Test
    public void getOrder() {
        String orderId = "1";
        service.getOrder(orderId);

        verify(orderDao).get(orderId);
    }
}