package com.kapok.controller;

import com.kapok.service.CommandService;
import com.kapok.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1")
public class CommandController {

    @Autowired
    private CommandService commandService;

    @PostMapping("/command/{command}")
    @ResponseBody
    public Object receiveCommand(@RequestHeader(value = "X-Redis-Client-Id", required = false) String clientId,
                                 @PathVariable String command,
                                 @RequestBody String params) {
        log.info("Received command [" + command + " " + params + "] from a client whose id is: " + clientId);
        return commandService.executeCommand(clientId, command, params);
    }

}
