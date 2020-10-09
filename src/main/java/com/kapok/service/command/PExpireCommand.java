package com.kapok.service.command;

import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;

public class PExpireCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;

    private String key;

    private Long msTimestamp;

    public PExpireCommand(RedisServer redisServer, String key, Long msTimestamp) {
        this.redisServer = redisServer;
        this.key = key;
        this.msTimestamp = msTimestamp;
    }

    @Override
    public String execute() {
        int selectedDbIndex = redisServer.getSelectedDbIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.pexpireat(key, msTimestamp);
        return "OK";
    }

}
