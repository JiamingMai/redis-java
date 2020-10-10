package com.kapok.service.command;

import com.kapok.model.RedisClient;
import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;

public class ExpireCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;
    private RedisClient redisClient;

    private String key;

    private Long seconds;

    public ExpireCommand(RedisServer redisServer, RedisClient redisClient, String key, Long seconds) {
        this.redisServer = redisServer;
        this.redisClient = redisClient;
        this.key = key;
        this.seconds = seconds;
    }

    @Override
    public String execute() {
        int selectedDbIndex = redisClient.getDatabaseIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.expire(key, seconds);
        return "OK";
    }

}
