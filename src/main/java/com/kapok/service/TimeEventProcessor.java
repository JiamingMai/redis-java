package com.kapok.service;

import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class TimeEventProcessor {

    @Autowired
    private RedisServer redisServer;

    @Scheduled(cron = "* * * * * ?")
    public void cleanExpiredKeys() {
        long currentTimestamp = System.currentTimeMillis();
        for (RedisDatabase database : redisServer.getDatabases()) {
            // find out the expired keys
            Set<String> expiredKeys = new HashSet<>();
            Map<String, Long> expires = database.getExpires();
            for (String key : expires.keySet()) {
                if (currentTimestamp > expires.get(key)) {
                    expiredKeys.add(key);
                }
            }
            // clean these keys
            Map<String, Object> dictionary = database.getDictionary();
            for (String expiredKey : expiredKeys) {
                dictionary.remove(expiredKey);
                expires.remove(expiredKey);
            }
        }
    }

}
