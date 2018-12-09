package com.es.phoneshop.service;

import com.es.phoneshop.model.LimitedSizeList;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.exception.NoSuchProductException;

import javax.servlet.http.HttpServletRequest;

public class DataLoader {
    private ProductDao productDao;
    private RecentlyViewedService recentlyViewedService;

    public DataLoader(){
        productDao = ArrayListProductDao.getInstance();
        recentlyViewedService = RecentlyViewedServiceImpl.getInstance();
    }

    public Integer loadQuantity(HttpServletRequest request, String quantityParameter) throws NumberFormatException{
        String quantityString = request.getParameter(quantityParameter);
        return Integer.parseUnsignedInt(quantityString);
    }

    public Product loadProductFromURI(HttpServletRequest request) throws NumberFormatException, NoSuchProductException {
        String uri = request.getRequestURI();
        int idIndex = uri.lastIndexOf("/");
        String stringId = uri.substring(idIndex + 1);
        Long id = Long.parseLong(stringId);
        return productDao.getProduct(id);
    }

    public LimitedSizeList<Product> loadRecentlyViewedProductList(HttpServletRequest request){
        return recentlyViewedService.getList(request.getSession());
    }
}
