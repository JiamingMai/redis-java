package com.kapok.service.command;

import com.kapok.model.RedisClient;
import com.kapok.model.RedisServer;
import com.kapok.service.CommandService;
import com.kapok.util.Constant;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Data
@Service
public class CommandFactory {

    @Value("${service.command.rdb-save-path}")
    private String rdbSavePath;

    public Command createCommand(
            RedisServer redisServer,
            RedisClient redisClient,
            String commandStr,
            String params) {
        String key;
        String value;
        String[] values;
        String[] keyAndValues;
        Command command = null;
        switch (commandStr) {
            case Constant.SELECT:
                int selectedDbIndex = Integer.valueOf(params);
                return new SelectCommand(redisServer, redisClient, selectedDbIndex);
            case Constant.SET:
                keyAndValues = params.split(" ");
                key = keyAndValues[0];
                value = keyAndValues[1];
                return new SetCommand(redisServer, redisClient, key, value);
            case Constant.HSET:
                keyAndValues = params.split(" ");
                key = keyAndValues[0];
                values = new String[keyAndValues.length - 1];
                for (int i = 1; i < keyAndValues.length; i++) {
                    values[i-1] = keyAndValues[i];
                }
                return new HSetCommand(redisServer, redisClient, key, values);
            case Constant.RPUSH:
                keyAndValues = params.split(" ");
                key = keyAndValues[0];
                values = new String[keyAndValues.length - 1];
                for (int i = 1; i < keyAndValues.length; i++) {
                    values[i-1] = keyAndValues[i];
                }
                return new RPushCommand(redisServer, redisClient, key, values);
            case Constant.EXISTS:
                key = params;
                return new ExistsCommand(redisServer, redisClient, key);
            case Constant.GET:
                key = params;
                return new GetCommand(redisServer, redisClient, key);
            case Constant.DEL:
                key = params;
                return new DelCommand(redisServer, redisClient, key);
            case Constant.PEXPIRE:
                keyAndValues = params.split(" ");
                key = keyAndValues[0];
                value = keyAndValues[1];
                return new PExpireCommand(redisServer, redisClient, key, Long.valueOf(value));
            case Constant.EXPIRE:
                keyAndValues = params.split(" ");
                key = keyAndValues[0];
                value = keyAndValues[1];
                return new ExpireCommand(redisServer, redisClient, key, Long.valueOf(value));
            case Constant.PEXPIREAT:
                keyAndValues = params.split(" ");
                key = keyAndValues[0];
                value = keyAndValues[1];
                return new PExpireAtCommand(redisServer, redisClient, key, Long.valueOf(value));
            case Constant.EXPIREAT:
                keyAndValues = params.split(" ");
                key = keyAndValues[0];
                value = keyAndValues[1];
                return new ExpireAtCommand(redisServer, redisClient, key, Long.valueOf(value));
            case Constant.PERSIST:
                key = params;
                return new PersistCommand(redisServer, redisClient, key);
            case Constant.PTTL:
                key = params;
                return new PttlCommand(redisServer, redisClient, key);
            case Constant.TTL:
                key = params;
                return new TtlCommand(redisServer, redisClient, key);
            case Constant.SAVE:
                return new SaveCommand(redisServer, redisClient, rdbSavePath);
            default:
        }
        return command;
    }

}
