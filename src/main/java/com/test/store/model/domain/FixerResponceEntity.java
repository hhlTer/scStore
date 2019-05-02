package com.test.store.model.domain;

import java.util.Map;

public class FixerResponceEntity {
    public boolean success;
    public int timestamp;
    public String base;
    public String date;
    public Map<String, Float> rates;
}
