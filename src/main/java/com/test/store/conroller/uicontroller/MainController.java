package com.test.store.conroller.uicontroller;

import com.test.store.model.domain.Order;
import com.test.store.model.domain.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private Store store;

    @GetMapping("/")
    public RedirectView index(){
        return new RedirectView("/main");
    }

    @GetMapping("/main")
    public String main(){
        //TODO change to service
        List<Order> localOrders = store.getStore();
        for (Order p:
                localOrders) {
            System.out.println(p);
        }
        return "index";
    }

}
