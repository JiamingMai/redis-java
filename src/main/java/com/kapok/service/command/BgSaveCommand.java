package com.kapok.service.command;

import com.alibaba.fastjson.JSON;
import com.kapok.model.RedisServer;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.PrintWriter;

@Slf4j
public class BgSaveCommand implements Command<String> {

    // the receiver of this command
    private RedisServer redisServer;

    private String rdbSavePath;

    public BgSaveCommand(RedisServer redisServer, String rdbSavePath) {
        this.redisServer = redisServer;
        this.rdbSavePath = rdbSavePath;
    }

    @Override
    public String execute() {
        String rdbContent = JSON.toJSONString(redisServer);
        try (PrintWriter printWriter = new PrintWriter(new File(rdbSavePath))) {
            printWriter.println(rdbContent);
            printWriter.flush();
        } catch (Exception e) {
            log.error("encountered error when saving rdb. ", e);
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
