package com.kapok.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class RedisConf {

    private static String rdbSavePath;

    @Value("${service.command.rdb-save-path}")
    public void setRdbSavePath(String rdbSavePath) {
        this.rdbSavePath = rdbSavePath;
    }

    public static String getRdbSavePath() {
        return rdbSavePath;
    }

}
