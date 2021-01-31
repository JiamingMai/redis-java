package com.kapok.model.cluster;

import lombok.Data;

@Data
public class ClusterLink {

    private long createTime;

    private int fd;

    private String sendBuffer;

    private String receiveBuffer;

    private ClusterNode node;

}
