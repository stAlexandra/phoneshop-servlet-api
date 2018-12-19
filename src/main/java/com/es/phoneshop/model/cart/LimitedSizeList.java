package com.es.phoneshop.model.cart;

import java.util.ArrayList;

public class LimitedSizeList<T> extends ArrayList<T> {

    private int maxSize;

    public LimitedSizeList(int size) {
        this.maxSize = size;
    }

    @Override
    public boolean add(T element) {
        boolean r = super.add(element);
        if (size() > maxSize) {
            removeRange(0, size() - maxSize);
        }
        return r;
    }
}

