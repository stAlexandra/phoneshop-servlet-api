package com.es.phoneshop.dao;

import com.es.phoneshop.exception.NoSuchItemException;
import com.es.phoneshop.model.product.Product;
import org.junit.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ArrayListProductDaoTest
{
    private ArrayListProductDao productDao;
    private int initSize;

    @Before
    public void setUp() {
        productDao = ArrayListProductDao.getInstance();
        List<Product> productList = new ArrayList<>();

        Currency usd = Currency.getInstance("USD");
        productList.add(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100), usd, 100, "https"));
        productList.add(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 2, "https"));
        productList.add(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, "https"));
        productList.add(new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https"));

        initSize = productList.size();
        productList.forEach(product -> productDao.save(product));
    }

    @After
    public void tearDown(){
        productDao.deleteAll();
    }

    @Test
    public void testGetInstance() {
        ArrayListProductDao instance = ArrayListProductDao.getInstance();
        assertNotNull(instance);
    }

    @Test
    public void testFindProducts(){
        assertEquals(initSize, productDao.findProducts("", "", false).size());
    }

    @Test
    public void testFindSamsung(){
        assertEquals(3, productDao.findProducts("Samsung", "", false).size());
    }

    @Test
    public void testFindSortedByDescription(){
        assertEquals("Apple iPhone", productDao.findProducts("", "description", true).get(0).getDescription());
    }

    @Test
    public void testFindMostExpensive(){
        assertEquals("Samsung Galaxy S III", productDao.findProducts("", "price", false).get(0).getDescription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveEmptyProduct(){
        Product product = new Product();
        productDao.save(product);
        assertEquals(initSize, productDao.findProducts("", "" , false).size());
    }

    @Test
    public void testSaveValidProduct(){
        productDao.save(new Product(123L, "str", "smk", new BigDecimal(200), Currency.getInstance("USD"), 100, "https"));
        assertEquals(initSize + 1, productDao.findProducts("", "" ,false).size());
    }

    @Test(expected = RuntimeException.class)
    public void testSaveExistedProduct(){
        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100),  Currency.getInstance("USD"), 100, "https"));
    }

    @Test
    public void testGetProduct(){
        long id = 2L;
        Product product = productDao.get(id);
        assertNotNull(product);
    }

    @Test(expected = NoSuchItemException.class)
    public void testGetProductNotExist(){
        long id = 999L;
        productDao.get(id);
    }

    @Test(expected = NoSuchItemException.class)
    public void testDeleteProduct(){
       // int previousSize = productDao.findProducts("", "", false).size();
        long id = 4L;
        productDao.delete(id);
        assertEquals(initSize - 1, productDao.findProducts("", "", false).size());
        productDao.get(id);
    }

    @Test(expected = NoSuchItemException.class)
    public void testDeleteProductNotExist(){
        long id = 888L;
        productDao.delete(id);
    }

}
