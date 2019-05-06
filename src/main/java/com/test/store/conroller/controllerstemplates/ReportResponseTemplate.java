package com.test.store.conroller.controllerstemplates;

import com.test.store.conroller.validation.user.forms.ValidationResult;
import com.test.store.utils.CurrencyEnum;

public class ReportResponseTemplate {

    private ValidationResult validationResult;
    private CurrencyEnum currencyCalc;
    private double amountResult;
    private int year;
    private String message;

    public String getMessage() {
        return message;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    public void setValidationResult(ValidationResult initValidationResult) {
        validationResult = initValidationResult;
        message = validationResult.getDescription();
    }

    public CurrencyEnum getCurrencyCalc() {
        return currencyCalc;
    }

    public void setCurrencyCalc(CurrencyEnum initCurrencyCalc) {
        currencyCalc = initCurrencyCalc;
    }

    public double getAmountResult() {
        return amountResult;
    }

    public void setAmountResult(double initAmountResult) {
        amountResult = initAmountResult;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int initYear) {
        year = initYear;
    }
}
