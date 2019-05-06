package com.test.store.model.domain;

import com.test.store.utils.CurrencyEnum;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Store {

    private List<Order> store = new ArrayList<>();
    {
        for (int i = 1; i < 5; i++) {
            Order p =new Order();
            p.setPrice(i + 2 * 3);
            p.setAmount(i*i);
            p.setDate(new Date());
            p.setProductName("Name" + i);
            p.setCurrency(CurrencyEnum.EUR);
            store.add(p);
        }
        Date localDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(localDate);
        cal.setTimeInMillis(localDate.getTime() - (long) 1000*60*60*24*365*3);
        localDate = cal.getTime();
        for (int i = 5; i < 11; i++) {
            Order p =new Order();
            p.setPrice(i + 2 * 3);
            p.setAmount(i*i);
            p.setDate(localDate);
            p.setProductName("Name" + i+3);
            p.setCurrency(CurrencyEnum.values()[i]);
            store.add(p);
        }
    }

    public List<Order> getStore() {
        return store;
    }

}
