package com.kapok.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RedisDatabase {

    private Map<String, Object> dictionary = new HashMap<>();

    public void set(String key, String value) {
        dictionary.put(key, value);
    }

    public Object get(String key) {
        return dictionary.get(key);
    }

}
