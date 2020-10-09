package com.kapok.service.command;

import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;

public class ExpireAtCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;

    private String key;

    private Long timestamp;

    public ExpireAtCommand(RedisServer redisServer, String key, Long timestamp) {
        this.redisServer = redisServer;
        this.key = key;
        this.timestamp = timestamp;
    }

    @Override
    public String execute() {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.expireat(key, timestamp);
        return "OK";
    }

}
