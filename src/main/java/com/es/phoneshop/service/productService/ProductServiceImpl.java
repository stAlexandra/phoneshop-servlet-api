package com.es.phoneshop.service.productService;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.exception.NoSuchProductException;
import com.es.phoneshop.model.product.ProductDao;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductServiceImpl() {
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

    public Product getProduct(Long productId) throws NoSuchProductException {
        ProductDao productDao = ArrayListProductDao.getInstance();
        return productDao.getProduct(productId);
    }

    public Product getProduct(String productId) throws NumberFormatException, NoSuchProductException {
        ProductDao productDao = ArrayListProductDao.getInstance();
        return productDao.getProduct(Long.parseUnsignedLong(productId));
    }

    public Product getProduct(HttpServletRequest request) throws NumberFormatException, NoSuchProductException {
        String uri = request.getRequestURI();
        int idIndex = uri.lastIndexOf("/");
        String stringId = uri.substring(idIndex + 1);
        Long id = Long.parseLong(stringId);

        ProductDao productDao = ArrayListProductDao.getInstance();
        return productDao.getProduct(id);
    }

    public List<Product> getFilteredProducts(String query, String sortField, boolean sortOrder) {
        ProductDao productDao = ArrayListProductDao.getInstance();
        return productDao.findProducts(query, sortField, sortOrder);
    }
}
