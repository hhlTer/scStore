package com.test.store.web;

public enum HttpFixerUriBuilderParameters {
    SCHEME("http"),
    HOST("data.fixer.io"),
    PATH_LATEST("/api/latest"),
    PATH("/api"),
    ACCESS_KEY_PARAMETER("access_key"),
    SYMBOLS_PARAMETER("symbols");

    private String name;

    HttpFixerUriBuilderParameters(String name){
        this.name = name;
    }

    public String value() {
        return name;
    }
}
