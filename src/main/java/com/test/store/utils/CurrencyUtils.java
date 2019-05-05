package com.test.store.utils;

import com.test.store.web.HttpFixerUriBuilderParameters;
import com.test.store.web.Request;
import com.test.store.model.domain.FixerResponceEntity;
import com.test.store.model.domain.Order;
import com.test.store.model.enums.CurrencyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CurrencyUtils {

    @Autowired
    @Qualifier(value = "fixerRequest")
    private Request<FixerResponceEntity> request;

    private CurrencyEnum mainCurrency;
    private List<Order> orders;
    
    private Map<CurrencyEnum, Float> convertingToEURTable;

    public float calculate(CurrencyEnum mainCurrency, List<Order> orders){
        
        this.mainCurrency = mainCurrency;
        this.orders = orders;
        
        //execute GET request to fixer.io and init all currencies converting in order list + main currency
        Map<String, String> parameters = generateParametersUsingOrders();
        FixerResponceEntity fixerResponseEntity = request.GET(parameters, FixerResponceEntity.class);
        this.convertingToEURTable = determineConvertingCurrencyMap(fixerResponseEntity);

        return calculateAll();
    }

    private float calculateAll() {
        Double result = orders.stream()
                .mapToDouble(p -> p.getPrice() * p.getAmount() * converting(mainCurrency, p.getCurrency()))
                .sum();
        return Float.parseFloat(normalize(result));
    }

    private String normalize(Double number) {
        float epsilon = 0.004f; // 4 tenths of a cent
        if (Math.abs(Math.round(number) - number) < epsilon) {
            return String.format("%10.0f", number);
        } else {
            return String.format("%10.2f", number);
        }
    }

    private float converting(CurrencyEnum toCurrency, CurrencyEnum fromCurrency) {
        return convertingToEURTable.get(toCurrency) / convertingToEURTable.get(fromCurrency);
    }

    private Map<String, String> generateParametersUsingOrders() {
        String currenciesParameter = generateCurrencyStringParameter();
        Map<String, String> parameters = new HashMap<>();
        parameters.put(HttpFixerUriBuilderParameters.SYMBOLS_PARAMETER.value(), currenciesParameter);
        return parameters;
    }

    private Map<CurrencyEnum, Float> determineConvertingCurrencyMap(FixerResponceEntity initResult) {
        return initResult.rates.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> CurrencyEnum.valueOf(e.getKey()),
                        Map.Entry::getValue
                ));
    }


    private String generateCurrencyStringParameter() {
        String result = orders.stream()
                .map(p -> p.getCurrency().name())
                .distinct()
                .collect(Collectors.joining(", "));
        //add main currency to string parameters
        result = result.contains(mainCurrency.name()) ? result : result + ", " + mainCurrency.name();
        return result;
    }
}
