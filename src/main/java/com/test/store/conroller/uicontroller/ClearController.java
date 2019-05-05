package com.test.store.conroller.uicontroller;

import com.test.store.conroller.validation.user.forms.UserResponseDataFormValidation;
import com.test.store.conroller.validation.user.forms.ValidationResult;
import com.test.store.model.domain.Order;
import com.test.store.model.service.ServiceStoreInterface;
import com.test.store.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ClearController {

    @Autowired
    @Qualifier(value = "simpleArrayListServiceImplementation")
    private ServiceStoreInterface storeInterface;

    @Autowired
    private UserResponseDataFormValidation userResponseDataFormValidation;

    /**
     * <p>- calling from TopMenu.html.</p>
     * <p>- Redirect to page with determining date to clear orders.</p>
     * <p>- Adding to form ArrayList of all dates where order is executed</p>
     * @return ModelAndView object with link to determineClearingProducts.html
     */
    @GetMapping("/to_clear_order")
    public ModelAndView determineClearingProducts(){
        //define all the dates when the product was purchased
        List<String> listOfDatePurchased = determineAllDatesWhenAnyProductWasPurchased();
        ModelAndView maw = new ModelAndView("operations/determineClearingProducts");
        maw.addObject("dateStringList", listOfDatePurchased);
        return maw;
    }

    /**
     * <p>- calling from determineClearingProduct.html page</p>
     * <p>- clearing all orders in definitions day</p>
     * @param date allowed from determineClearingProduct.html page form
     * @return count of cleared orders
     */
    @GetMapping("/clearAllProductsInDay")
    @ResponseBody
    public String clearing(
            @RequestParam String date
    ){
        ValidationResult localValidationResult = userResponseDataFormValidation.validationNumber(date);
        if (localValidationResult != ValidationResult.OK){
            return localValidationResult.toString();
        }
        int countOfClearedProducts = storeInterface.clearAllOrdersInDate(date);
        return "Cleared " + countOfClearedProducts + " orders";
    }

    private List<String> determineAllDatesWhenAnyProductWasPurchased() {
        return storeInterface.getAllOrders().stream()
                .map(Order::getDate)
                .map(DateUtils::parseYearFromDate)
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
