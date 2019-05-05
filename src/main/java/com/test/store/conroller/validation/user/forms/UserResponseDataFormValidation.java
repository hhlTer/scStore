package com.test.store.conroller.validation.user.forms;

import com.test.store.model.enums.CurrencyEnum;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

@Component
public class UserResponseDataFormValidation {

    public ValidationResult validationValues(
            String name,
            String date,
            String price,
            String currency,
            String amount
    ) {
        if (isEmptyAnyFields(name, date, price, currency, amount)){
            return ValidationResult.EMPTY_FIELDS;
        }

        Pattern p = Pattern.compile("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$");
        if (!p.matcher(date).matches()){
            return ValidationResult.NOT_VALID_DATE_VALUE;
        }
        DecimalFormat df = new DecimalFormat();
        float priceResult = 0;
        int amountResult = 0;
        try {
            priceResult = df.parse(price).floatValue();
        } catch (ParseException initE) {
            return ValidationResult.WRONG_PRICE_FORMAT;
        }

        try {
            amountResult = df.parse(amount).intValue();
        } catch (ParseException initE) {
            return ValidationResult.WRONG_AMOUNT_FORMAT;
        }

        if ((priceResult == 0) || (amountResult == 0)){
            return ValidationResult.A_VALUE_IS_ZERO;
        }

        if (priceResult < 0 || amountResult < 0) {
            return ValidationResult.NEGATIVE_VALUE;
        }

        return ValidationResult.OK;

    }

    private boolean isEmptyAnyFields(String ... fields) {
        for (String s :
                fields) {
            if (s.length() < 1) {
                return true;
            }
        }
        return false;
    }

    public ValidationResult validationNumber(String initDate) {
        if (isEmptyAnyFields(initDate)){
            return ValidationResult.EMPTY_FIELDS;
        }
        DecimalFormat df = new DecimalFormat();
        try {
            df.parse(initDate).intValue();
        } catch (ParseException initE) {
            return ValidationResult.WRONG_YEAR_FORMAT;
        }
        return ValidationResult.OK;
    }

    public ValidationResult yearValidation(String string){
        //TODO:
        return null;
    }

    public ValidationResult validationCurrency(String initCurrency) {
        if (isEmptyAnyFields(initCurrency)){
            return ValidationResult.EMPTY_FIELDS;
        }
        try {
            CurrencyEnum localCurrencyEnum = CurrencyEnum.valueOf(initCurrency);
        } catch (IllegalArgumentException e){
            return ValidationResult.UNKNOWN_CURRENCY;
        }
        return ValidationResult.OK;
    }
}
