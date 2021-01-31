package com.kapok.model.cluster;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ClusterState {

    private ClusterNode myself;

    private int currentEpoch;

    private int state;

    private int size;

    private Map<String, ClusterNode> nodes;

    // slot related fields

    /**
     * map slot index to ClusterNode
     */
    private Map<Integer, ClusterNode> slots;

    private Map<Integer, List<String>> slotsToKeys;

    private Map<Integer, ClusterNode> importingSlotsFrom;

    private Map<Integer, ClusterNode> migratingSlotsTo;


}
