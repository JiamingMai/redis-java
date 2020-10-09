package com.kapok.service;

import com.alibaba.fastjson.JSON;
import com.kapok.model.RedisDatabase;
import com.kapok.model.RedisServer;
import com.kapok.model.RedisServerState;
import com.kapok.service.command.Command;
import com.kapok.service.command.CommandFactory;
import com.kapok.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

@Slf4j
@Service
public class CommandService implements InitializingBean {

    @Autowired
    private CommandFactory commandFactory;

    @Autowired
    private RedisServer redisServer;

    @Value("${service.command.rdb-save-path}")
    private String rdbSavePath;

    public Object executeCommand(String commandStr, String params) {
        requireNotSaving();
        synchronized (this) {
            Command command = commandFactory.createCommand(redisServer, commandStr, params);
            return command.execute();
        }
    }

    public void requireNotSaving() {
        if (isRedisSaving()) {
            throw new RuntimeException("cannot do with any command during saving. ");
        }
    }

    private boolean isRedisSaving() {
        return redisServer.getRedisServerState().isSaving();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadRdb();
    }

    private void loadRdb() {
        File rdbFile = new File(rdbSavePath);
        if (!rdbFile.exists()) {
            return;
        }
        StringBuffer buffer = new StringBuffer();
        try (BufferedReader reader = new BufferedReader(new FileReader(rdbSavePath))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String rdbContent = buffer.toString();
            RedisServer tmpRedisServer = JSON.parseObject(rdbContent, RedisServer.class);
            redisServer.setDatabases(tmpRedisServer.getDatabases());
        } catch (Exception e) {
            log.error("cannot load rdb file. ", e);
        }
    }
}
