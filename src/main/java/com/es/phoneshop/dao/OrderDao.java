package com.es.phoneshop.dao;

import com.es.phoneshop.model.order.Order;

public interface OrderDao {
    Order getOrder(String id);
    void save(Order order);
}
