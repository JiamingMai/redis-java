package com.kapok.controller;

import com.kapok.service.CommandService;
import com.kapok.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class CommandController {

    @Autowired
    private CommandService commandService;

    @PostMapping("/{command}")
    @ResponseBody
    public Object receiveCommand(@PathVariable String command, @RequestBody String params) {
        return commandService.executeCommand(command, params);
    }

}
