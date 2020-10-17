package com.kapok.service;

import com.kapok.model.HeartbeatInfo;
import com.kapok.model.RedisServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HeartbeatService {

    @Autowired
    private RedisServer redisServer;

    public HeartbeatInfo responseHeartbeat() {
        HeartbeatInfo heartbeatInfo = new HeartbeatInfo();
        heartbeatInfo.setHost(redisServer.getRedisServerState().getHost());
        heartbeatInfo.setPort(redisServer.getRedisServerState().getPort());
        heartbeatInfo.setCommandIndex(redisServer.getCommandIndex().get());
        return heartbeatInfo;
    }

}
