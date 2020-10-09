package com.kapok.service.command;

import com.kapok.model.RedisServer;

public class SelectCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;

    private Integer databaseIndex;

    public SelectCommand(RedisServer redisServer, Integer databaseIndex) {
        this.redisServer = redisServer;
        this.databaseIndex = databaseIndex;
    }

    @Override
    public String execute() {
        redisServer.setSelectedDbIndex(databaseIndex);
        return "OK";
    }

}
