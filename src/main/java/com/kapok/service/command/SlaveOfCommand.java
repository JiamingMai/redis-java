package com.kapok.service.command;

import com.kapok.model.RedisClient;
import com.kapok.model.RedisServer;
import com.kapok.util.Constant;
import com.kapok.util.NetworkUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class SlaveOfCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;
    private RedisClient redisClient;

    private String host;
    private Integer port;

    private RestTemplate restTemplate = new RestTemplate();

    public SlaveOfCommand(RedisServer redisServer, RedisClient redisClient, String host, Integer port) {
        this.redisServer = redisServer;
        this.redisClient = redisClient;
        this.host = host;
        this.port = port;
    }

    @Override
    public String execute() {

        if (NetworkUtil.isLocalHost(host) && port.equals(redisServer.getRedisServerState().getPort())) {
            long start = System.currentTimeMillis();
            SyncCommand syncCommand = new SyncCommand(redisServer, redisClient, redisServer.getRedisServerState().getHost(), redisServer.getRedisServerState().getPort());
            syncCommand.execute();
            // notice that we don't need to set data received from itself
            long end = System.currentTimeMillis();
            log.info((end - start) + " ms");
            return "OK";
        } else {
            String url = "http://" + host + ":" + port + "/v1/" + Constant.SYNC;
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Redis-Client-Id", redisServer.getRedisServerState().getRunId());
            String param = redisServer.getRedisServerState().getHost() + " " + redisServer.getRedisServerState().getPort();
            HttpEntity<String> objectHttpEntity = new HttpEntity<>(param, headers);
            RedisServer copiedRedisServer = restTemplate.postForObject(url, objectHttpEntity, RedisServer.class);
            redisServer.setDatabases(copiedRedisServer.getDatabases());
            return "OK";
        }
    }
}
