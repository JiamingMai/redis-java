package com.kapok.service.command;

import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;

public class HSetCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;

    private String key;

    private String[] values;

    public HSetCommand(RedisServer redisServer, String key, String...values) {
        this.redisServer = redisServer;
        this.key = key;
        this.values = values;
    }

    @Override
    public String execute() {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.hset(key, values);
        return "OK";
    }

}
