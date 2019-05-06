package com.test.store.conroller.uicontroller;

import com.test.store.conroller.controllerstemplates.ReportResponseTemplate;
import com.test.store.conroller.validation.user.forms.UserResponseDataFormValidation;
import com.test.store.conroller.validation.user.forms.ValidationResult;
import com.test.store.model.domain.Order;
import com.test.store.model.service.ServiceStoreInterface;
import com.test.store.utils.CurrencyEnum;
import com.test.store.utils.CurrencyUtils;
import com.test.store.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CalculateAmountController {

    @Autowired
    private ServiceStoreInterface storeService;

    @Autowired
    private UserResponseDataFormValidation userResponseDataFormValidation;

    @Autowired
    private CurrencyUtils currencyUtils;

    @GetMapping("/choice_year_to_report")
    public ModelAndView choiceYear(){
        ModelAndView mav = new ModelAndView("report/choiceYear");
        List<String> years = determineYearsWithOrdersInStore(storeService.getAllOrders());
        List<String> currencies = Arrays.stream(CurrencyEnum.values())
                .map(Enum::name)
                .collect(Collectors.toCollection(ArrayList::new));
        mav.addObject("years", years);
        mav.addObject("currencies", currencies);
        return mav;
    }


    @GetMapping("/calculateReport")
    @ResponseBody
    public ReportResponseTemplate reportByYear(
            @RequestParam(value = "date") String year,
            @RequestParam String currency
    ) {

        ValidationResult localValidationResult = userResponseDataFormValidation.validationNumber(year);
        {
            if (localValidationResult != ValidationResult.OK) {
                return errorTemplate(localValidationResult);
            }
            localValidationResult = userResponseDataFormValidation.validationCurrency(currency);
            if (localValidationResult != ValidationResult.OK) {
                return errorTemplate(localValidationResult);
            }
        }

        float costProduction = calculateCostProduction(year, currency);
        ReportResponseTemplate template = new ReportResponseTemplate();
        template.setAmountResult(costProduction);
        template.setCurrencyCalc(CurrencyEnum.valueOf(currency));
        template.setValidationResult(localValidationResult);
        template.setYear(Integer.parseInt(year));
        return template;
    }

    private float calculateCostProduction(String year, String currency) {
        List<Order> orderListByYear = storeService.getAllOrdersByYear(DateUtils.parseDateFromString(year));
        return currencyUtils.calculate(CurrencyEnum.valueOf(currency), orderListByYear);
    }

    private ReportResponseTemplate errorTemplate(ValidationResult initValidationResult) {
        ReportResponseTemplate rt = new ReportResponseTemplate();
        rt.setValidationResult(initValidationResult);
        return rt;
    }

    private List<String> determineYearsWithOrdersInStore(List<Order> initAllOrders) {
        return initAllOrders.stream()
                .map(p -> DateUtils.parseYearFromDate(p.getDate()))
                .distinct()
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
