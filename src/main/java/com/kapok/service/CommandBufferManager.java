package com.kapok.service;

import com.google.common.collect.ImmutableList;
import com.kapok.model.CommandRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class CommandBufferManager {

    private final AtomicInteger commandId = new AtomicInteger(0);

    private final Integer MAX_BUFFER_SIZE = 100;

    private final LinkedList<CommandRecord> commandRecords = new LinkedList<>();

    public synchronized void addCommandRecord(String commandFullStr) {
        if (commandRecords.size() < MAX_BUFFER_SIZE) {
            commandRecords.offer(new CommandRecord(commandId.getAndAdd(1), commandFullStr));
        } else {
            commandRecords.poll();
            commandRecords.offer(new CommandRecord(commandId.getAndAdd(1), commandFullStr));
        }
    }

    /**
     * get commands given the start id and the offset
     * @param startCommandId
     * @param offset the length/size of commands, the number of commands to be selected
     * @return
     */
    public synchronized List<CommandRecord> getCommands(Integer startCommandId, Integer offset) {
        int startIndex = -1;
        for (int i = 0; i < commandRecords.size(); i++) {
            CommandRecord commandRecord = commandRecords.get(i);
            if (commandRecord.getId().equals(startCommandId)) {
                startIndex = i;
                break;
            }
        }
        if (startCommandId == -1) {
            return ImmutableList.of();
        }
        // copy command records
        List<CommandRecord> selectedRecords = new LinkedList<>();
        for (int i = startIndex, k = 0; i < commandRecords.size() && k < offset ; i++, k++) {
            CommandRecord commandRecord = commandRecords.get(i);
            selectedRecords.add(commandRecord);
        }
        return selectedRecords;
    }

}
