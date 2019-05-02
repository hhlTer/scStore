package com.test.store.controller.uicontroller;

import com.test.store.conroller.uicontroller.OperationController;
import com.test.store.model.domain.Order;
import com.test.store.model.domain.Store;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = { ApplicationConfig.class })
@WebAppConfiguration
public class TestOperationController {

    public static OperationController staticOperationController;

    @Autowired
    private Store store;

    @BeforeClass
    public static void beforeClass(){
        staticOperationController = new OperationController();
    }

    @Test
    public void privateDetermineAllDatesWhenAnyProductWasPurchasedTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method method = OperationController.class.getDeclaredMethod("determineAllDatesWhenAnyProductWasPurchased", List.class);
        method.setAccessible(true);

        List<Order> storeList = store.getStore();
        DateFormat localDateFormat = new SimpleDateFormat("dd-MM-yy");
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Order p = new Order();
            p.setPrice(i);
            p.setProductName("name" + i);
            p.setDate(new Date());
            p.setAmount(i*i);
            p.setPrice(i+2*i);
            storeList.add(p);
            input.add(localDateFormat.format(p.getDate()));
        }
        List<String> output = (List<String>) method.invoke(staticOperationController);
        assertTrue(input.equals(output));


    }
}
