package com.kapok.service.command;

import com.kapok.model.RedisClient;
import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;

public class ExpireAtCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;
    private RedisClient redisClient;

    private String key;

    private Long timestamp;

    public ExpireAtCommand(RedisServer redisServer, RedisClient redisClient, String key, Long timestamp) {
        this.redisServer = redisServer;
        this.redisClient = redisClient;
        this.key = key;
        this.timestamp = timestamp;
    }

    @Override
    public String execute() {
        int selectedDbIndex = redisClient.getDatabaseIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.expireat(key, timestamp);
        redisServer.updateCommandIndex();
        return "OK";
    }

}
