package com.es.phoneshop.service.productService;

import com.es.phoneshop.dao.ArrayListProductDao;
import com.es.phoneshop.exception.NoSuchItemException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.dao.ProductDao;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao;
    private ProductServiceImpl() {
        productDao = ArrayListProductDao.getInstance();
    }

    private static volatile ProductServiceImpl instance = null;
    private static final Object lock = new Object();

    public static ProductServiceImpl getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ProductServiceImpl();
                }
            }
        }
        return instance;
    }

    public Product getProduct(Long productId) throws NoSuchItemException {
        return productDao.get(productId);
    }

    public Product getProduct(String productId) throws NumberFormatException, NoSuchItemException {
        return productDao.get(Long.parseUnsignedLong(productId));
    }

    public Product getProduct(HttpServletRequest request) throws NumberFormatException, NoSuchItemException {
        String uri = request.getRequestURI();
        int idIndex = uri.lastIndexOf("/");
        String stringId = uri.substring(idIndex + 1);
        Long id = Long.parseLong(stringId);

        return productDao.get(id);
    }

    public List<Product> getFilteredProducts(String query, String sortField, boolean sortOrder) {
        return productDao.findProducts(query, sortField, sortOrder);
    }
}
