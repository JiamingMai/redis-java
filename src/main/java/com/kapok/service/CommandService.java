package com.kapok.service;

import com.alibaba.fastjson.JSON;
import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.PrintWriter;

@Slf4j
@Service
public class CommandService {

    @Autowired
    private RedisServer redisServer;

    @Value("${service.command.rdb-save-path}")
    private String rdbSavePath;

    public synchronized void select(int databaseIndex) {
        redisServer.setSelectedDbIndex(databaseIndex);
    }

    public synchronized void set(String key, String value) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.set(key, value);
    }

    public synchronized void hset(String key, String...values) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.hset(key, values);
    }

    public synchronized void rpush(String key, String...values) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.rpush(key, values);
    }

    public synchronized Integer exists(String key) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        return database.exists(key);
    }

    public synchronized Object get(String key) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        return database.get(key);
    }

    public synchronized void delete(String key) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.delete(key);
    }

    public synchronized void pexpire(String key, int milliseconds) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.pexpire(key, milliseconds);
    }

    public synchronized void expire(String key, int seconds) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.expire(key, seconds);
    }

    public synchronized void pexpireat(String key, long msTimestamp) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.pexpireat(key, msTimestamp);
    }

    public synchronized void expireat(String key, long timestamp) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.expireat(key, timestamp);
    }

    public synchronized void persist(String key) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.persist(key);
    }

    public synchronized Long pttl(String key) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        return database.pttl(key);
    }

    public synchronized Long ttl(String key) {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        return database.ttl(key);
    }

    public synchronized void save() {
        String rdbContent = JSON.toJSONString(redisServer);
        try (PrintWriter printWriter = new PrintWriter(new File(rdbSavePath))) {
            printWriter.println(rdbContent);
            printWriter.flush();
        } catch (Exception e) {
            log.error("encountered error when saving rdb. ", e);
        }
    }

    public void bgsave() {
        // TODO: implement this method
    }

}
