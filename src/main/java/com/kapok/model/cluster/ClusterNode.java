package com.kapok.model.cluster;

import lombok.Data;

import java.util.List;


@Data
public class ClusterNode {

    private long createTime;

    private String nodeName;

    // REDIS_NODE_MASTER or REDIS_NODE_SLAVE
    private int flags;

    private int configEpoch;

    private String ip;

    private int port;

    private ClusterLink link;

    // size = 16384 / 8 = 2048
    private Byte[] slots;

    /**
     * used for replicate
     */
    private ClusterNode slaveOf;

    /**
     * a list for recording nodes which think this node is offline
     */
    private List<ClusterNodeFailReport> failReports;

}
