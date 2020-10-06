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
        String[] values;
        String[] keyAndValues;
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
            case Constant.HSET:
                keyAndValues = params.split(" ");
                key = keyAndValues[0];
                values = new String[keyAndValues.length - 1];
                for (int i = 1; i < keyAndValues.length; i++) {
                    values[i-1] = keyAndValues[i];
                }
                commandService.hset(key, values);
                break;
            case Constant.RPUSH:
                keyAndValues = params.split(" ");
                key = keyAndValues[0];
                values = new String[keyAndValues.length - 1];
                for (int i = 1; i < keyAndValues.length; i++) {
                    values[i-1] = keyAndValues[i];
                }
                commandService.rpush(key, values);
                break;
            case Constant.GET:
                key = params;
                return commandService.get(key);
            case Constant.DEL:
                key = params;
                commandService.delete(key);
            default:
        }
        return "OK";
    }

}
