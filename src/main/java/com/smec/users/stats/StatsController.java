package com.smec.users.stats;

import java.util.List;
import java.util.stream.Collectors;

import com.smec.users.exceptions.IllegalReferenceException;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.tools.web.BadHttpRequest;

@RestController
@RequestMapping(value = "/stats")
public class StatsController {

    @Autowired
    private IStatsService eventService;

    @GetMapping()
    public List<StatsDto> list() {
        List<StatsEntry> entries = eventService.fetchAllEvents();
        return entries.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @PostMapping
    public StatsDto store(@RequestBody StatsDto entity) throws BadHttpRequest {
        try {
            return mapToDto(eventService.store(mapToEntry(entity), entity.getAccountId()));
        } catch (IllegalReferenceException e) {
            throw new BadHttpRequest(e);
        }
    }

    private StatsEntry mapToEntry(StatsDto entity) {
        StatsEntry result = new StatsEntry();
        result.setId(entity.getId());
        result.setTime(entity.getTime());
        result.setType(entity.getType());
        return result;
    }

    private StatsDto mapToDto(StatsEntry entry) {
        StatsDto result = new StatsDto();
        result.setAccountId(entry.getAccount().getId());
        result.setTime(entry.getTime());
        result.setId(entry.getId());
        result.setType(entry.getType());
        return result;
    }
}