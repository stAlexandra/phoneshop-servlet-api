package com.es.phoneshop.service.recentlyViewedService;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.LimitedSizeList;

import javax.servlet.http.HttpSession;

public class RecentlyViewedServiceImpl implements RecentlyViewedService {
    private final static String RECENTLY_VIEWED_ATTRIBUTE = "viewedProducts";
    private final static Integer MAX_NUM_VIEWED = 3;

    private static volatile RecentlyViewedService recentlyViewedService;
    private final static Object lock = new Object();

    private RecentlyViewedServiceImpl() {
    }

    public static RecentlyViewedService getInstance() {
        if (recentlyViewedService == null) {
            synchronized (lock) {
                if (recentlyViewedService == null) {
                    recentlyViewedService = new RecentlyViewedServiceImpl();
                }
            }
        }
        return recentlyViewedService;
    }

    @Override
    public LimitedSizeList<Product> getList(HttpSession session) {
        LimitedSizeList<Product> viewedProducts = (LimitedSizeList<Product>) session.getAttribute(RECENTLY_VIEWED_ATTRIBUTE);

        if (viewedProducts == null) {
            viewedProducts = new LimitedSizeList<>(MAX_NUM_VIEWED);
            session.setAttribute(RECENTLY_VIEWED_ATTRIBUTE, viewedProducts);
        }
        return viewedProducts;
    }

    @Override
    public void addToList(Product product, LimitedSizeList<Product> productList) {
        productList.remove(product);
        productList.add(product);
    }
}
