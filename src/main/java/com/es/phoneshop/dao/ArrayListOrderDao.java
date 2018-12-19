package com.es.phoneshop.dao;

import com.es.phoneshop.exception.NoSuchOrderException;
import com.es.phoneshop.model.order.Order;

import java.util.ArrayList;
import java.util.List;

public class ArrayListOrderDao implements OrderDao{
    private final List<Order> orders = new ArrayList<>();

    private static volatile ArrayListOrderDao arrayListOrderDao = null;
    private static final Object lock = new Object();
    private ArrayListOrderDao() {
    }

    public static ArrayListOrderDao getInstance() {
        if (arrayListOrderDao == null) {
            synchronized (lock) {
                if (arrayListOrderDao == null) {
                    arrayListOrderDao = new ArrayListOrderDao();
                }
            }
        }
        return arrayListOrderDao;
    }

    public Order getOrder(String id) {
        synchronized (orders){
            return orders.stream()
                    .filter(order -> id.equals(order.getSecureId()))
                    .findAny()
                    .orElseThrow(()-> new NoSuchOrderException(id));
        }
    }

    @Override
    public void save(Order order) {
        String id = order.getSecureId();
        if(id == null){
            throw new IllegalArgumentException("Null order id.");
        }
        synchronized (orders){
            if(orderExist(id)){
                throw new RuntimeException("Order with id " + id + "already exists.");
            } else {
                orders.add(order);
            }
        }
    }

    private boolean orderExist(String id){
        return orders.stream().anyMatch(order -> id.equals(order.getSecureId()));
    }

}
