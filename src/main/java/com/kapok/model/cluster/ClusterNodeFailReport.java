package com.kapok.model.cluster;

import lombok.Data;

@Data
public class ClusterNodeFailReport {

    private ClusterNode node;

    private long lastReportTimestamp;

}
