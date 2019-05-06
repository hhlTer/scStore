package com.test.store.model.domain;

import com.test.store.utils.CurrencyEnum;

import java.util.Date;

public class Order {

    private String productName;
    private Date date;
    private int amount;
    private CurrencyEnum currency;
    private double price;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String initProductName) {
        productName = initProductName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date initDate) {
        date = initDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int initAmount) {
        amount = initAmount;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum initCurrency) {
        currency = initCurrency;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double initPrice) {
        price = initPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "productName='" + productName + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                ", currency=" + currency +
                ", price=" + price +
                '}';
    }
}
