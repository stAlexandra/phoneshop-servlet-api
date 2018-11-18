package com.es.phoneshop.model.product;

import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ArrayListProductDaoTest
{
    private static ProductDao productDao;

    @BeforeClass
    public static void setUp() {
        productDao = ArrayListProductDao.getInstance();
        List<Product> productList = new ArrayList<>();

        Currency usd = Currency.getInstance("USD");
        productList.add(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https"));
        productList.add(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 2, "https"));
        productList.add(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https"));
        productList.add(new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https"));

        productList.forEach(product -> productDao.save(product));
    }

    @Test
    public void testGetInstance() {
        ArrayListProductDao instance = ArrayListProductDao.getInstance();
        assertNotNull(instance);
    }

    @Test
    public void testFindProducts(){
        assertEquals(4, productDao.findProducts().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveEmptyProduct(){
        Product product = new Product();
        productDao.save(product);
        assertEquals(4, productDao.findProducts().size());
    }

    @Test
    public void testSaveValidProduct(){
        productDao.save(new Product(123L, "str", "smk", new BigDecimal(200), Currency.getInstance("USD"), 100, "https"));
        assertEquals(5, productDao.findProducts().size());
    }

    @Test(expected = RuntimeException.class)
    public void testSaveExistedProduct(){
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100),  Currency.getInstance("USD"), 100, "https"));
    }

    @Test
    public void testGetProduct(){
        long id = 2L;
        Product product = productDao.getProduct(id);
        assertNotNull(product);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetProductNotExist(){
        long id = 999L;
        productDao.getProduct(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteProduct(){
        int previousSize = productDao.findProducts().size();
        long id = 3L;
        productDao.delete(id);
        assertEquals(previousSize - 1, productDao.findProducts().size());
        productDao.getProduct(id);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteProductNotExist(){
        long id = 888L;
        productDao.delete(id);
    }

}
