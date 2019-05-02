package com.test.store.conroller.uicontroller;

import com.test.store.model.service.ServiceStoreInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShowController {

    @Autowired
    @Qualifier("simpleArrayListServiceImplementation")
    private ServiceStoreInterface storeInterface;

    /**
     * called from templates/fragments/TopMenu.html
     * @return ModelAndView object with link to templates/sow/showOrders.html
     * and list of all orders
     */
    @GetMapping("/listProducts")
    public ModelAndView listAllProducts(){
        ModelAndView mav = new ModelAndView("/show/showOrders");
        mav.addObject("orders", storeInterface.getAllOrders());
        return mav;
    }
}
