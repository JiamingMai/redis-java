package com.kapok.model;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Data
@Repository
public class RedisServer {

    private final int DEFAULT_DATABASE_NUM = 16;

    private List<RedisDatabase> databases = new ArrayList<>();

    public RedisServer() {
        for (int i = 0; i < DEFAULT_DATABASE_NUM; i++) {
            databases.add(new RedisDatabase());
        }
    }

    private int selectedDbIndex;

}
