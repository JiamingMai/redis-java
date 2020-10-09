package com.kapok.service.command;

import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;

public class ExpireCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;

    private String key;

    private Long seconds;

    public ExpireCommand(RedisServer redisServer, String key, Long seconds) {
        this.redisServer = redisServer;
        this.key = key;
        this.seconds = seconds;
    }

    @Override
    public String execute() {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.expire(key, seconds);
        return "OK";
    }

}
