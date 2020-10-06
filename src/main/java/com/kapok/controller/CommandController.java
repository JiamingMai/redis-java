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
        String key;
        String value;
        switch (command) {
            case Constant.SELECT:
                int selectedDbIndex = Integer.valueOf(params);
                commandService.select(selectedDbIndex);
                break;
            case Constant.SET:
                String[] keyAndValue = params.split(" ");
                key = keyAndValue[0];
                value = keyAndValue[1];
                commandService.set(key, value);
                break;
            case Constant.GET:
                key = params;
                return commandService.get(key);
            default:
        }
        return null;
    }

}
