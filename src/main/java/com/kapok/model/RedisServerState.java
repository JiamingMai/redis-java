package com.kapok.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@Slf4j
@Data
@Repository
public class RedisServerState {

    private String runId = UUID.randomUUID().toString();

    @Value("${service.redis-server-host}")
    private String host;

    @Value("${server.port}")
    private Integer port;

    private boolean saving;

    /*
    public String getHost() {
        try {
            InetAddress ip4 = Inet4Address.getLocalHost();
            return ip4.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("cannot get host address. ", e);
            return null;
        }
    }
    */

}
