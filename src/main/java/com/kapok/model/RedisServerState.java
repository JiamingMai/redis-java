package com.kapok.model;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class RedisServerState {

    private boolean saving;

}
