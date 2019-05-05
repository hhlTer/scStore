package com.test.store.conroller.validation.user.forms;



public enum ValidationResult {
    A_VALUE_IS_ZERO("Price or amount cannot be a zero or empty"),
    EMPTY_FIELDS("One or more field is empty or wrong format.\n Please complete all fields."),
    NOT_VALID_DATE_VALUE("Not valid date value. \nDate must be in accordance to yyyy-MM-dd format. Please choose correct date if appropriate field"),
    NEGATIVE_VALUE("Price and/or amount cannot be less than zero"),
    OK("Ok"),
    WRONG_PRICE_FORMAT("Wrong price format. Please, enter correct price value (1.23)."),
    WRONG_AMOUNT_FORMAT("Wrong amount format. Please, enter correct amount value."),
    WRONG_YEAR_FORMAT("Wrong year format. Please, enter correct year value.\nFor example: 2019"),
    UNKNOWN_CURRENCY("Unknown currency");

    private String description;

    ValidationResult(String description){
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}