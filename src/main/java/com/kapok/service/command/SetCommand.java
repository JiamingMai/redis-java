package com.kapok.service.command;

import com.kapok.model.RedisClient;
import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;
import com.kapok.model.RedisServerState;

public class SetCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;
    private RedisClient redisClient;

    private String key;

    private String value;

    public SetCommand(RedisServer redisServer, RedisClient redisClient, String key, String value) {
        this.redisServer = redisServer;
        this.redisClient = redisClient;
        this.key = key;
        this.value = value;
    }

    @Override
    public String execute() {
        int selectedDbIndex = redisClient.getDatabaseIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.set(key, value);
        redisServer.updateCommandIndex();
        return "OK";
    }

}
