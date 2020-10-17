package com.kapok.service.command;

import com.kapok.model.RedisClient;
import com.kapok.model.RedisServer;

public class SelectCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;
    private RedisClient redisClient;

    private Integer databaseIndex;

    public SelectCommand(RedisServer redisServer, RedisClient redisClient, Integer databaseIndex) {
        this.redisServer = redisServer;
        this.redisClient = redisClient;
        this.databaseIndex = databaseIndex;
    }

    @Override
    public String execute() {
        redisClient.setDatabaseIndex(databaseIndex);
        redisClient.setSelectedDatabase(redisServer.getDatabases().get(databaseIndex));
        redisServer.updateCommandIndex();
        return "OK";
    }

}
