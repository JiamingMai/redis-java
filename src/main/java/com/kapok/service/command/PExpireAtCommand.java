package com.kapok.service.command;

import com.kapok.model.RedisClient;
import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;

public class PExpireAtCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;
    private RedisClient redisClient;

    private String key;

    private Long milliseconds;

    public PExpireAtCommand(RedisServer redisServer, RedisClient redisClient, String key, Long milliseconds) {
        this.redisServer = redisServer;
        this.redisClient = redisClient;
        this.key = key;
        this.milliseconds = milliseconds;
    }

    @Override
    public String execute() {
        int selectedDbIndex = redisClient.getDatabaseIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.pexpire(key, milliseconds);
        redisServer.updateCommandIndex();
        return "OK";
    }

}
