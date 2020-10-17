package com.kapok.model;

import lombok.Data;

@Data
public class CommandRecord {

    private Integer id;

    private String commandFullStr;

    public CommandRecord(Integer id, String commandFullStr) {
        this.id = id;
        this.commandFullStr = commandFullStr;
    }

}
