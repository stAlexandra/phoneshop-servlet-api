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

    @Test(expected = NullPointerException.class)
    public void testNotSavingEmptyProducts(){
        Product product = new Product();
        productDao.save(product);
    }

    @Test
    public void testPut1product(){
        Product product = new Product();
        product.setId(5757L);
        product.setStock(1);
        product.setPrice(new BigDecimal(100));

        productDao.save(product);

        assertEquals(1, productDao.findProducts().size());
    }

    @Test
    public void testGetProduct(){
        Product product123 = new Product();
        product123.setId(567L);
        productDao.save(product123);

        assertEquals(productDao.getProduct(567L).getId(), product123.getId());
    }

    @Test(expected = NullPointerException.class)
    public void testGetNonExistingProduct(){
        productDao.getProduct(123L);
    }

    @Test(expected = RuntimeException.class)
    public void testGetDuplicatedProduct(){
        Product product = new Product();
        product.setId(123L);
        Product product1 = new Product();
        product1.setId(123L);

        assertEquals(product.getId(), product1.getId());

        productDao.save(product);
        productDao.save(product1);

        productDao.getProduct(123L);
    }

    @Test
    public void testDeleteProduct(){
        Product product = new Product();
        Long id = 12345L;
        product.setId(id);

        productDao.save(product);
        assertEquals(1, productDao.getAllProducts().size());

        productDao.delete(id);
        assertEquals(0, productDao.getAllProducts().size());
    }
}
