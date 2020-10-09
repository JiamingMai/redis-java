package com.kapok.service.command;

import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;

public class PttlCommand implements Command<Object> {

    // the receiver of this command
    private RedisServer redisServer;

    private String key;

    public PttlCommand(RedisServer redisServer, String key) {
        this.redisServer = redisServer;
        this.key = key;
    }

    @Override
    public Object execute() {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        return database.pttl(key);
    }

}
