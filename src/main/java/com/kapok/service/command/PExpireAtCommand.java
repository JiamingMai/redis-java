package com.kapok.service.command;

import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;

public class PExpireAtCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;

    private String key;

    private Long milliseconds;

    public PExpireAtCommand(RedisServer redisServer, String key, Long milliseconds) {
        this.redisServer = redisServer;
        this.key = key;
        this.milliseconds = milliseconds;
    }

    @Override
    public String execute() {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.pexpire(key, milliseconds);
        return "OK";
    }

}
