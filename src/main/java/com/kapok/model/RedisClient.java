package com.kapok.model;

import lombok.Data;

@Data
public class RedisClient {

    private int databaseIndex;

    private RedisDatabase selectedDatabase;

}
