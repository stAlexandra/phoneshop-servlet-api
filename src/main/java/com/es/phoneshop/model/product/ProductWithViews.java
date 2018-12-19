package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.Product;

public class ProductWithViews extends Product{

    private Long views;

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }
}
