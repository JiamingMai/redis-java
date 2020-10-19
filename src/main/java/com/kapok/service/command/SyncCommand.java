package com.kapok.service.command;

import com.alibaba.fastjson.JSON;
import com.kapok.model.RedisClient;
import com.kapok.model.RedisServer;
import com.kapok.model.RedisSlave;
import com.kapok.service.SlaveManager;
import com.kapok.util.RedisConf;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SyncCommand implements Command<RedisServer> {

    // the receiver of this command
    private RedisServer redisServer;
    private RedisClient redisClient;
    private SlaveManager slaveManager;

    // host and port of the slave server which the master server transports RDB file to
    private String host;
    private Integer port;

    public SyncCommand(RedisServer redisServer, RedisClient redisClient, SlaveManager slaveManager, String host, Integer port) {
        this.redisServer = redisServer;
        this.redisClient = redisClient;
        this.slaveManager = slaveManager;
        this.host = host;
        this.port = port;
    }

    @Override
    public RedisServer execute() {
        long start = System.currentTimeMillis();
        BgSaveCommand bgSaveCommand = new BgSaveCommand(redisServer, RedisConf.getRdbSavePath());
        String rdbContent = bgSaveCommand.executeInternal();
        RedisServer copiedRedisServer = JSON.parseObject(rdbContent, RedisServer.class);

        // register slave
        RedisSlave slave = new RedisSlave();
        slave.setHost(host);
        slave.setPort(port);
        slave.setCommandIndex(redisServer.getCommandIndex().get());
        slave.setLastHeartbeatTimestamp(System.currentTimeMillis());
        slaveManager.registerSlave(slave);
        long end = System.currentTimeMillis();
        log.info((end - start) + " ms");
        return copiedRedisServer;
    }
}
