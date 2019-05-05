package com.test.store.conroller.uicontroller;

import com.test.store.utils.DateUtils;
import com.test.store.utils.CurrencyUtils;
import com.test.store.conroller.validation.user.forms.UserResponseDataFormValidation;
import com.test.store.conroller.validation.user.forms.ValidationResult;
import com.test.store.model.domain.Order;
import com.test.store.model.enums.CurrencyEnum;
import com.test.store.model.service.ServiceStoreInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReportController {

    @Autowired
    private ServiceStoreInterface storeService;

    @Autowired
    private CurrencyUtils currencyUtils;

    @Autowired
    private UserResponseDataFormValidation userResponseDataFormValidation;

    @GetMapping("/choice_year_to_report")
    public ModelAndView choiceYear(){
        ModelAndView mav = new ModelAndView("report/choiceYear");
        List<String> years = determineYearsWithOrdersInStore(storeService.getAllOrders());
        List<String> currencies = Arrays.stream(CurrencyEnum.values())
                .map(p -> p.name())
                .collect(Collectors.toCollection(ArrayList::new));
        mav.addObject("years", years);
        mav.addObject("currencies", currencies);
        return mav;
    }

    @GetMapping("/reportByYear")
    @ResponseBody
    public String reportByYear(
            @RequestParam(value = "date") String year,
            @RequestParam String currency
    ) {
        ValidationResult localValidationResult = userResponseDataFormValidation.validationNumber(year);
        if (localValidationResult != ValidationResult.OK){
            return localValidationResult.toString();
        }
        localValidationResult = userResponseDataFormValidation.validationCurrency(currency);
        if (localValidationResult != ValidationResult.OK){
            return localValidationResult.toString();
        }
        List<Order> orderListByYear = storeService.getAllOrdersByYear(DateUtils.parseDateFromString(year));
        float amountInYear = currencyUtils.calculate(CurrencyEnum.valueOf(currency), orderListByYear);
        return "Amount of orders in " + year +
                " as of the date: " + new Date() +
                " = " + amountInYear +
                " " + currency;
    }

    private List<String> determineYearsWithOrdersInStore(List<Order> initAllOrders) {
        return initAllOrders.stream()
                .map(p -> DateUtils.parseYearFromDate(p.getDate()))
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
