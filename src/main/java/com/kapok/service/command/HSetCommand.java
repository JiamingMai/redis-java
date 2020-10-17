package com.kapok.service.command;

import com.kapok.model.RedisClient;
import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;

public class HSetCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;
    private RedisClient redisClient;

    private String key;

    private String[] values;

    public HSetCommand(RedisServer redisServer, RedisClient redisClient, String key, String...values) {
        this.redisServer = redisServer;
        this.redisClient = redisClient;
        this.key = key;
        this.values = values;
    }

    @Override
    public String execute() {
        int selectedDbIndex = redisClient.getDatabaseIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.hset(key, values);
        redisServer.updateCommandIndex();
        return "OK";
    }

}
