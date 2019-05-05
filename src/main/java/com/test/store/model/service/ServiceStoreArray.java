package com.test.store.model.service;

import com.test.store.model.domain.Order;
import com.test.store.model.repository.StoreRepositoryInterface;
import com.test.store.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service(value = "simpleArrayListServiceImplementation")
public class ServiceStoreArray implements ServiceStoreInterface {

    @Autowired
    @Qualifier(value = "simpleArrayListImplementation")
    private StoreRepositoryInterface repositoryStore;

    @Override
    public List<Order> getAllOrders() {
        return repositoryStore.findAll();
    }

    @Override
    public void addOrder(Order initOrder) {
        if (initOrder != null) {
            repositoryStore.save(initOrder);
        }
    }

    @Override
    public List<Order> getAllOrdersByYear(Date initDate) {
        if (initDate != null){
            return repositoryStore.findOrdersByYear(initDate);
        }
        return repositoryStore.findAll();
    }

    @Override
    public int clearAllOrdersInDate(String initDate) {
        List<Order> localOrders = repositoryStore.findOrdersByYear(DateUtils.parseDateFromString(initDate));
        repositoryStore.deleteOrders(localOrders);
        return localOrders.size();
    }
}
