package com.es.phoneshop.dao;

import com.es.phoneshop.model.order.Order;

public class ArrayListOrderDao extends Dao<String, Order> implements OrderDao {
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
}
