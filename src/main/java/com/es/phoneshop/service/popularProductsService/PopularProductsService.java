package com.es.phoneshop.service.popularProductsService;

import com.es.phoneshop.model.cart.LimitedSizeList;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductWithViews;

public interface PopularProductsService {
    void updateProductViews(Product product);
    LimitedSizeList<ProductWithViews> getTopList(int maxNumProductsInList);
}
