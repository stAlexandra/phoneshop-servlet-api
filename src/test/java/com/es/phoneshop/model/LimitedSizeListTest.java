package com.es.phoneshop.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LimitedSizeListTest {
    private LimitedSizeList list;
    private int maxSize = 3;

    @Before
    public void setUp(){
        list = new LimitedSizeList(maxSize);
    }

    @Test
    public void testAddNoMoreThanMax() {
        Object element = new Object();

        for(int i = 0; i < maxSize + 1; i++) {
            list.add(element);
        }

        assertEquals(maxSize, list.size());
    }
}