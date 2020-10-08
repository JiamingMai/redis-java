package com.kapok.model;

import lombok.Data;

import java.util.*;

@Data
public class RedisDatabase {

    private Map<String, Object> dictionary = new HashMap<>();

    private Map<String, Long> expires = new HashMap<>();

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

    public Integer exists(String key) {
        if (dictionary.containsKey(key)) {
            return 1;
        } else {
            return 0;
        }
    }

    public void pexpire(String key, int milliseconds) {
        pexpireat(key, System.currentTimeMillis() + milliseconds);
    }

    public void expire(String key, int seconds) {
        pexpire(key, seconds * 1000);
    }

    public void pexpireat(String key, long msTimestamp) {
        if (dictionary.containsKey(key)) {
            expires.put(key, msTimestamp);
        }
    }

    public void expireat(String key, long timestamp) {
        pexpireat(key, timestamp * 1000L);
    }

}
