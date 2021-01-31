package com.kapok.model.cluster;

import lombok.Data;

@Data
public class ClusterMessage {

    private int totalLen;

    private int type;

    private int count;

    private int currentEpoch;

    private int configEpoch;

    private String sender;

    // size = 16384 / 8 = 2048
    private Byte[] mySlots;

    private String slaveOf;

    private int port;

    private int flags;

    private int clusterState;

    private ClusterMessageData data;

}
