package com.kapok.service.command;

import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;

public class PersistCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;

    private String key;

    public PersistCommand(RedisServer redisServer, String key) {
        this.redisServer = redisServer;
        this.key = key;
    }

    @Override
    public String execute() {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.persist(key);
        return "OK";
    }

}
