package com.kapok.model;

import lombok.Data;

@Data
public class HeartbeatInfo {

    private String host;

    private Integer port;

    private Integer commandIndex;

}
