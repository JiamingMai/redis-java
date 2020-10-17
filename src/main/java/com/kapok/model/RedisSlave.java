package com.kapok.model;

import lombok.Data;

@Data
public class RedisSlave {

    private String runId;

    private Integer commandIndex;

    private String host;

    private Integer port;

    private Long lastHeartbeatTimestamp;

}
