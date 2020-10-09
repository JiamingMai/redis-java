package com.kapok.model;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
public class RedisDatabase implements Serializable {

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

    public boolean isExpire(String key) {
        if (!expires.containsKey(key)) {
            return false;
        }
        long expireTimestamp = expires.get(key);
        return expireTimestamp > System.currentTimeMillis() ? false : true;
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

    public void pexpire(String key, Long milliseconds) {
        pexpireat(key, System.currentTimeMillis() + milliseconds);
    }

    public void expire(String key, Long seconds) {
        pexpire(key, seconds * 1000L);
    }

    public void pexpireat(String key, long msTimestamp) {
        if (dictionary.containsKey(key)) {
            expires.put(key, msTimestamp);
        }
    }

    public void expireat(String key, long timestamp) {
        pexpireat(key, timestamp * 1000L);
    }

    public void persist(String key) {
        expires.remove(key);
    }

    public Long pttl(String key) {
        if (!expires.containsKey(key)) {
            return -1L;
        }
        long msTimestamp = expires.get(key);
        long left = msTimestamp - System.currentTimeMillis();
        return left;
    }

    public Long ttl(String key) {
        if (!expires.containsKey(key)) {
            return -1L;
        }
        return pttl(key) / 1000L;
    }

}
