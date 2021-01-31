package com.kapok.model.cluster;

import lombok.Data;

@Data
public class ClusterMessageDataGossip {

    private String nodeName;

    private long pingSentLastTimestamp;

    private long pongReceivedLastTimestamp;

    private String ip;

    private int port;

    private int flag;

}
