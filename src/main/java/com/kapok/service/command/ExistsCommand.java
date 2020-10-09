package com.kapok.service.command;

import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;

public class ExistsCommand implements Command<Integer> {

    // the receiver of this command
    private RedisServer redisServer;

    private String key;

    public ExistsCommand(RedisServer redisServer, String key) {
        this.redisServer = redisServer;
        this.key = key;
    }

    @Override
    public Integer execute() {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        return database.exists(key);
    }

}
