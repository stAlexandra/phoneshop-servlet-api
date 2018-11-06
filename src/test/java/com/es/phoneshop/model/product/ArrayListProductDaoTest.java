package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = new ArrayListProductDao();
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts().isEmpty());
    }

    @Test
    public void testNotAddingEmptyProducts(){
        Product product = new Product();
        productDao.save(product);

        assertEquals(0, productDao.findProducts().size());
    }

    @Test
    public void testHas1product(){
        Product product = new Product();
        product.setStock(1);
        product.setPrice(new BigDecimal(100));

        productDao.save(product);

        assertEquals(1, productDao.findProducts().size());
    }
}
