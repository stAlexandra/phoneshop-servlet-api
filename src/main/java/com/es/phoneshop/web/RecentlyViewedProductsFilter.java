package com.es.phoneshop.web;

import com.es.phoneshop.model.exception.NoSuchProductException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.service.DataLoader;
import com.es.phoneshop.service.RecentlyViewedService;
import com.es.phoneshop.service.RecentlyViewedServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.rmi.ServerException;

public class RecentlyViewedProductsFilter implements Filter {
    private RecentlyViewedService recentlyViewedService;
    private DataLoader dataLoader;

    @Override
    public void init(FilterConfig filterConfig){
        recentlyViewedService = RecentlyViewedServiceImpl.getInstance();
        dataLoader = new DataLoader();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        try{
            Product product = dataLoader.loadProductFromURI(request);
            HttpSession httpSession = request.getSession();
            recentlyViewedService.addToList(product, recentlyViewedService.getList(httpSession));
        } catch (NumberFormatException | NoSuchProductException e){
            throw new ServerException(e.getMessage());
        }

        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
