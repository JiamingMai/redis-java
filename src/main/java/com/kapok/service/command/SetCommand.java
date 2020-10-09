package com.kapok.service.command;

import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;
import com.kapok.model.RedisServerState;

public class SetCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;

    private String key;

    private String value;

    public SetCommand(RedisServer redisServer, String key, String value) {
        this.redisServer = redisServer;
        this.key = key;
        this.value = value;
    }

    @Override
    public String execute() {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.set(key, value);
        return "OK";
    }

}
