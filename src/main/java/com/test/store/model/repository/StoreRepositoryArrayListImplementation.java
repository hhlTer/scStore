package com.test.store.model.repository;

import com.test.store.conroller.utils.DateUtils;
import com.test.store.model.domain.Order;
import com.test.store.model.domain.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository(value = "simpleArrayListImplementation")
public class StoreRepositoryArrayListImplementation implements StoreRepositoryInterface {

    @Autowired
    private Store store;

    @Override
    public void save(Order initOrder) {
        store.getStore().add(initOrder);
    }

    @Override
    public List<Order> findAll() {
        return store.getStore();
    }

    @Override
    public List<Order> findOrdersByYear(Date initDate) {
        ArrayList<Order> localOrders = store.getStore().stream()
                .filter(p -> DateUtils.parseYearFromDate(p.getDate()).equals(DateUtils.parseYearFromDate(initDate)))
                .collect(Collectors.toCollection(ArrayList::new));
        return localOrders;
    }

    @Override
    public void deleteOrders(List<Order> initOrders) {
        store.getStore().removeAll(initOrders);
    }
}
