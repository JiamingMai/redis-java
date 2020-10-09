package com.kapok.service.command;

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

    public Command createCommand(RedisServer redisServer, String commandStr, String params) {
        String key;
        String value;
        String[] values;
        String[] keyAndValues;
        Command command = null;
        switch (commandStr) {
            case Constant.SELECT:
                int selectedDbIndex = Integer.valueOf(params);
                return new SelectCommand(redisServer, selectedDbIndex);
            case Constant.SET:
                keyAndValues = params.split(" ");
                key = keyAndValues[0];
                value = keyAndValues[1];
                return new SetCommand(redisServer, key, value);
            case Constant.HSET:
                keyAndValues = params.split(" ");
                key = keyAndValues[0];
                values = new String[keyAndValues.length - 1];
                for (int i = 1; i < keyAndValues.length; i++) {
                    values[i-1] = keyAndValues[i];
                }
                return new HSetCommand(redisServer, key, values);
            case Constant.RPUSH:
                keyAndValues = params.split(" ");
                key = keyAndValues[0];
                values = new String[keyAndValues.length - 1];
                for (int i = 1; i < keyAndValues.length; i++) {
                    values[i-1] = keyAndValues[i];
                }
                return new RPushCommand(redisServer, key, values);
            case Constant.EXISTS:
                key = params;
                return new ExistsCommand(redisServer, key);
            case Constant.GET:
                key = params;
                return new GetCommand(redisServer, key);
            case Constant.DEL:
                key = params;
                return new DelCommand(redisServer, key);
            case Constant.PEXPIRE:
                keyAndValues = params.split(" ");
                key = keyAndValues[0];
                value = keyAndValues[1];
                return new PExpireCommand(redisServer, key, Long.valueOf(value));
            case Constant.EXPIRE:
                keyAndValues = params.split(" ");
                key = keyAndValues[0];
                value = keyAndValues[1];
                return new ExpireCommand(redisServer, key, Long.valueOf(value));
            case Constant.PEXPIREAT:
                keyAndValues = params.split(" ");
                key = keyAndValues[0];
                value = keyAndValues[1];
                return new PExpireAtCommand(redisServer, key, Long.valueOf(value));
            case Constant.EXPIREAT:
                keyAndValues = params.split(" ");
                key = keyAndValues[0];
                value = keyAndValues[1];
                return new ExpireAtCommand(redisServer, key, Long.valueOf(value));
            case Constant.PERSIST:
                key = params;
                return new PersistCommand(redisServer, key);
            case Constant.PTTL:
                key = params;
                return new PttlCommand(redisServer, key);
            case Constant.TTL:
                key = params;
                return new TtlCommand(redisServer, key);
            case Constant.SAVE:
                return new SaveCommand(redisServer, rdbSavePath);
            default:
        }
        return command;
    }

}
