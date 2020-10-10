package com.kapok.model;

import lombok.Data;

@Data
public class RedisClient {

    private String clientId;

    private Integer databaseIndex = 0;

    private RedisDatabase selectedDatabase;

    public RedisClient(String clientId) {
        this.clientId = clientId;
    }

}
