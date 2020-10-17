package com.kapok.service.command;

import com.kapok.model.RedisClient;
import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;

public class PExpireCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;
    private RedisClient redisClient;

    private String key;

    private Long msTimestamp;

    public PExpireCommand(RedisServer redisServer, RedisClient redisClient, String key, Long msTimestamp) {
        this.redisServer = redisServer;
        this.redisClient = redisClient;
        this.key = key;
        this.msTimestamp = msTimestamp;
    }

    @Override
    public String execute() {
        int selectedDbIndex = redisClient.getDatabaseIndex();
        RedisDatabase database = redisServer.getDatabases().get(selectedDbIndex);
        database.pexpireat(key, msTimestamp);
        redisServer.updateCommandIndex();
        return "OK";
    }

}
