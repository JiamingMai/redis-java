package com.kapok.model;

import lombok.Data;

@Data
public class RedisClient {

    private String clientRunId;

    private Integer databaseIndex = 0;

    private RedisDatabase selectedDatabase;

    public RedisClient(String clientRunId) {
        this.clientRunId = clientRunId;
    }

}
