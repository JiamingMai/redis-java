package com.kapok.service;

import com.kapok.model.HeartbeatInfo;
import com.kapok.model.RedisServer;
import com.kapok.model.RedisSlave;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Service
public class SlaveManager {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisServer redisServer;

    private final long SLAVE_EXPIRED_TIMEOUT_THRESHOLD = 60000;

    private ConcurrentMap<String, RedisSlave> slavesMap = new ConcurrentHashMap<>();

    public synchronized void registerSlave(RedisSlave redisSlave) {
        slavesMap.put(redisSlave.getRunId(), redisSlave);
    }

    public synchronized void syncCommandToSlaves(String command, String params) {
        String serverRunId = redisServer.getRedisServerState().getRunId();
        for (RedisSlave slave : slavesMap.values()) {
            sendCommand(serverRunId, slave.getHost(), slave.getPort(), command, params);
        }
    }

    private String sendCommand(String clientId, String host, Integer port, String command, String params) {
        // send command to redis server
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Redis-Client-Id", clientId);
        HttpEntity<String> objectHttpEntity = new HttpEntity<>(params, headers);
        String response = restTemplate.postForObject("http://"  + host + ":" + port + "/v1/command/" + command, objectHttpEntity, String.class);
        //System.out.println(response);
        return response;
    }

    @Scheduled(cron = "*/20 * * * * ?")
    public void checkSlaveHeartbeat() {
        // send PING heartbeat to every slave and get the heartbeat information from them
        for (RedisSlave slave : slavesMap.values()) {
            try {
            HeartbeatInfo heartbeatInfo = restTemplate.getForObject("http://"  + slave.getHost() + ":" + slave.getPort() + "/v1/hearbeat", HeartbeatInfo.class);
            slave.setCommandIndex(heartbeatInfo.getCommandIndex());
            slave.setLastHeartbeatTimestamp(System.currentTimeMillis());
            } catch (Exception e) {
                log.error("PING failed for " + slave.getHost() + ":" + slave.getPort(), e);
            }
        }
        // find out expired slaves which master cannot connect to
        List<String> slavesToBeRemoved = new ArrayList<>();
        long currentTimstamp = System.currentTimeMillis();
        for (String slaveRunId : slavesMap.keySet()) {
            try {
                RedisSlave slave = slavesMap.get(slaveRunId);
                if (currentTimstamp - slave.getLastHeartbeatTimestamp() > SLAVE_EXPIRED_TIMEOUT_THRESHOLD) {
                    slavesToBeRemoved.add(slaveRunId);
                }
            } catch (Exception e) {
                log.error("slave " + slaveRunId + " doesn't exist");
            }
        }
        // remove expired slaves
        for (String expiredSlaveRunId : slavesToBeRemoved) {
            slavesMap.remove(expiredSlaveRunId);
        }
    }

}
