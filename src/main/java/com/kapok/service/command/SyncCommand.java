package com.kapok.service.command;

import com.alibaba.fastjson.JSON;
import com.kapok.model.RedisClient;
import com.kapok.model.RedisServer;
import com.kapok.util.RedisConf;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SyncCommand implements Command<RedisServer> {

    // the receiver of this command
    private RedisServer redisServer;
    private RedisClient redisClient;

    // host and port of the slave server which the master server transports RDB file to
    private String host;
    private Integer port;

    public SyncCommand(RedisServer redisServer, RedisClient redisClient, String host, Integer port) {
        this.redisServer = redisServer;
        this.redisClient = redisClient;
        this.host = host;
        this.port = port;
    }

    @Override
    public RedisServer execute() {
        long start = System.currentTimeMillis();
        BgSaveCommand bgSaveCommand = new BgSaveCommand(redisServer, RedisConf.getRdbSavePath());
        String rdbContent = bgSaveCommand.executeInternal();
        RedisServer copiedRedisServer = JSON.parseObject(rdbContent, RedisServer.class);
        long end = System.currentTimeMillis();
        log.info((end - start) + " ms");
        return copiedRedisServer;
    }
}
