package com.test.store.model.service;

import com.test.store.model.domain.Order;

import java.util.Date;
import java.util.List;

public interface ServiceStoreInterface {
    List<Order> getAllOrders();
    void addOrder(Order p);
    List<Order> getAllOrdersByYear(Date initDate);
    int clearAllOrdersInDate(String initDate);
}
