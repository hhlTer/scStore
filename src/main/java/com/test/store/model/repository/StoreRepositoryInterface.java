package com.test.store.model.repository;

import com.test.store.model.domain.Order;

import java.util.Date;
import java.util.List;

public interface StoreRepositoryInterface {
    void save(Order initOrder);
    List<Order> findAll();
    List<Order> findOrdersByYear(Date initDate);
    void deleteOrders(List<Order> initOrders);
}
