package com.es.phoneshop.service.popularProductsService;

import com.es.phoneshop.dao.ArrayListPopularProductsDao;
import com.es.phoneshop.model.product.ProductWithViews;
import com.es.phoneshop.model.cart.LimitedSizeList;
import com.es.phoneshop.model.product.Product;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PopularProductsServiceImpl implements PopularProductsService {
    private final static String POPULAR_PRODUCTS_ATTRIBUTE = "popularProducts";
    private final static Integer MAX_NUM = 3;

    private ArrayListPopularProductsDao dao;

    private static volatile PopularProductsService instance;
    private final static Object lock = new Object();

    private PopularProductsServiceImpl() {
        dao = ArrayListPopularProductsDao.getInstance();
    }

    public static PopularProductsService getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new PopularProductsServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public LimitedSizeList<ProductWithViews> getTopList(int maxNumProductsInList) {
        LimitedSizeList<ProductWithViews> topList = new LimitedSizeList<>(maxNumProductsInList);
        List<ProductWithViews> sortedProductList = dao.getProducts().stream().sorted(Comparator.comparing(ProductWithViews::getViews).reversed()).collect(Collectors.toList());
        for(int i = 0; i < maxNumProductsInList; i++) {
            topList.add(sortedProductList.get(i));
        }
        return topList;
    }

    @Override
    public void updateProductViews (Product product){
//        Optional<ProductWithViews> optional = dao.getProducts().stream().filter(item -> product.getId().equals(item.getId())).findAny();
//        optional.ifPresent(item -> item.setViews(item.getViews()+1));
//        if(optional.isPresent()){
//            Long views = optional.get().getViews();
//            optional.get().setViews(views + 1);
//        } else {
//            ProductWithViews newProduct = new ProductWithViews(product, 1L);
//            dao.saveProduct(newProduct);
//        }

    }


}
