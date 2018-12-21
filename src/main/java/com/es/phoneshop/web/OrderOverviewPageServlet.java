package com.es.phoneshop.web;

import com.es.phoneshop.exception.NoSuchItemException;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.service.orderService.OrderService;
import com.es.phoneshop.service.orderService.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderOverviewPageServlet extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() throws ServletException{
        super.init();
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Order order = orderService.getOrder(request);
            request.setAttribute("order", order);
            request.getRequestDispatcher("/WEB-INF/pages/orderOverview.jsp").forward(request, response);

        } catch (NoSuchItemException e){
            response.sendError(404);
        }
    }
}
