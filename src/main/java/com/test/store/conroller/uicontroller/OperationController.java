package com.test.store.conroller.uicontroller;

import com.test.store.conroller.service.Validation;
import com.test.store.conroller.service.ValidationResult;
import com.test.store.conroller.utils.DateUtils;
import com.test.store.model.domain.Order;
import com.test.store.model.enums.CurrencyEnum;
import com.test.store.model.service.ServiceStoreInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OperationController {

    @Autowired
    @Qualifier(value = "simpleArrayListServiceImplementation")
    private ServiceStoreInterface storeInterface;

    @Autowired
    private Validation validation;

    /**
     * <p>- calling from TopMenu.html,</p>
     * <p>- redirection to page with ordering product form,</p>
     * <p>- add to form currency list 'currencyList'</p>
     * @return ModelAndView object with 'currencyList' and redirection to addOrder.html page
     */
    @GetMapping("/to_add_order")
    public ModelAndView fillOrder(){
        ModelAndView maw = new ModelAndView("/operations/addOrder");
        maw.addObject("currencyList", getListOfAllCurrencyFromCurrencyEnum());
        return maw;
    }

    /**
     * <p>- calling from /operations/addOrder.html page,</p>
     * <p>- allow fields from addOrder.html and save Order in Store</p>
     * @param name product name
     * @param date product date
     * @param price product price
     * @param currency product currency
     * @param amount amount of product in the order
     * @return {@link String} answer with creating Order data
     */
    @GetMapping("/addOrder")
    @ResponseBody
    public String addOrder(
            @RequestParam String name,
            @RequestParam String date,
            @RequestParam String price,
            @RequestParam String currency,
            @RequestParam String amount
            ){
        ValidationResult localValidationResult = validation.validationValues(name, date, price, currency, amount);
        if (localValidationResult != ValidationResult.OK){
            return localValidationResult.toString();
        }
        Order p = fillProduct(name, date, price, currency, amount);
        storeInterface.addOrder(p);
//        TODO: return preparing answer
//        String answer = prepareAnswer(p);
        return p.toString();
    }

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
        ModelAndView maw = new ModelAndView("/operations/determineClearingProducts");
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
        ValidationResult localValidationResult = validation.validationNumber(date);
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

    private Order fillProduct(String initName, String initDate, String initPrice, String initCurrency, String initAmount) {
        Order p = new Order();
        p.setAmount(Integer.parseInt(initAmount));
        p.setCurrency(CurrencyEnum.valueOf(initCurrency));
        p.setDate(DateUtils.parseDateFromString(initDate));
        p.setProductName(initName);
        //TODO string to float normalize
        p.setPrice(Float.parseFloat(initPrice));
        return p;
    }

    private List<String> getListOfAllCurrencyFromCurrencyEnum() {
        return Arrays.stream(CurrencyEnum.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
