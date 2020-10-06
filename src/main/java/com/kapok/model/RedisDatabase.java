package com.kapok.model;

import lombok.Data;

import java.util.*;

@Data
public class RedisDatabase {

    private Map<String, Object> dictionary = new HashMap<>();

    public void set(String key, String value) {
        dictionary.put(key, value);
    }

    public void hset(String key, String...values) {
        Set<String> set = new HashSet<>();
        for (String value : values) {
            set.add(value);
        }
        dictionary.put(key, set);
    }

    public void rpush(String key, String...values) {
        List<String> list = new ArrayList<>();
        for (String value : values) {
            list.add(value);
        }
        dictionary.put(key, list);
    }

    public Object get(String key) {
        return dictionary.get(key);
    }

    public void delete(String key) {
        dictionary.remove(key);
    }

}
