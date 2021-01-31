package com.kapok.model.cluster;

import lombok.Data;

@Data
public class ClusterMessageDataPublish {

    private int channelLen;

    private int messageLen;

    private Byte[] bulkData;

}
