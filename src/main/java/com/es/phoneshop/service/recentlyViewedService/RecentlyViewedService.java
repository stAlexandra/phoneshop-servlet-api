package com.es.phoneshop.service.recentlyViewedService;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.cart.LimitedSizeList;

import javax.servlet.http.HttpSession;

public interface RecentlyViewedService {
    LimitedSizeList<Product> getList(HttpSession session);
    void addToList(Product product, LimitedSizeList<Product> productList);
}
