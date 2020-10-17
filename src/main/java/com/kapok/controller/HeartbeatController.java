package com.kapok.controller;

import com.kapok.model.HeartbeatInfo;
import com.kapok.service.HeartbeatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1")
public class HeartbeatController {

    @Autowired
    private HeartbeatService heartbeatService;

    @GetMapping("/heartbeat")
    @ResponseBody
    public HeartbeatInfo heartbeat() {
        return heartbeatService.responseHeartbeat();
    }

}
