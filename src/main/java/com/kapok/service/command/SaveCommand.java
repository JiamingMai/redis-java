package com.kapok.service.command;

import com.alibaba.fastjson.JSON;
import com.kapok.model.RedisClient;
import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.PrintWriter;

@Slf4j
public class SaveCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;
    private RedisClient redisClient;

    private String rdbSavePath;

    public SaveCommand(RedisServer redisServer, RedisClient redisClient, String rdbSavePath) {
        this.redisServer = redisServer;
        this.redisClient = redisClient;
        this.rdbSavePath = rdbSavePath;
    }

    @Override
    public String execute() {
        try (PrintWriter printWriter = new PrintWriter(new File(rdbSavePath))) {
            setRedisSavingState();
            String rdbContent = JSON.toJSONString(redisServer);
            printWriter.println(rdbContent);
            printWriter.flush();
        } catch (Exception e) {
            log.error("encountered error when saving rdb. ", e);
        } finally {
            unsetRedisSavingState();
        }
        return "OK";
    }

    private void setRedisSavingState() {
        redisServer.getRedisServerState().setSaving(true);
    }

    private void unsetRedisSavingState() {
        redisServer.getRedisServerState().setSaving(false);
    }

}
