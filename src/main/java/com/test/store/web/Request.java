package com.test.store.web;

import java.util.Map;

public interface Request<T> {
    T GET(Map<String, String> parameters, Class<T> clazz);
}
