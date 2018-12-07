package com.es.phoneshop.service;

import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import com.es.phoneshop.model.product.exception.NoSuchProductException;

import javax.servlet.http.HttpServletRequest;

public class DataLoader {
    public Integer loadQuantity(HttpServletRequest request, String quantityParameter) throws NumberFormatException{
        String quantityString = request.getParameter(quantityParameter);
        return Integer.parseUnsignedInt(quantityString);
    }

    public Product loadProduct(HttpServletRequest request, ProductDao productDao) throws NumberFormatException, NoSuchProductException {
        String uri = request.getRequestURI();
        int lastSlashIndex = uri.lastIndexOf("/");
        String productId = uri.substring(lastSlashIndex + 1);
        long id = Long.parseLong(productId);

        return productDao.getProduct(id);
    }
}
