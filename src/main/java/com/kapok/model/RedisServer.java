package com.kapok.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@Repository
public class RedisServer implements Serializable {

    private final int DEFAULT_DATABASE_NUM = 16;

    private AtomicInteger commandIndex = new AtomicInteger(0);

    private List<RedisDatabase> databases = new ArrayList<>();

    @JSONField(serialize = false)
    @Transient
    private List<RedisClient> redisClients = new ArrayList<>();

    @JSONField(serialize = false)
    @Transient
    @Autowired
    private RedisServerState redisServerState;

    private int selectedDbIndex;

    public RedisServer() {
        for (int i = 0; i < DEFAULT_DATABASE_NUM; i++) {
            databases.add(new RedisDatabase());
        }
    }

    public void updateCommandIndex() {
        commandIndex.addAndGet(1);
    }

}
