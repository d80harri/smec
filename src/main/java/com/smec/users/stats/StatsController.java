package com.smec.users.stats;

import java.util.List;
import java.util.stream.Collectors;

import com.smec.users.base.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/accounts/{accountId}/stats")
public class StatsController {

    @Autowired
    private IStatsService eventService;

    @GetMapping()
    public List<StatsDto> list(@PathVariable("accountId") int accountId) {
        List<StatsEntry> entries = eventService.fetchAll(accountId);
        return entries.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private StatsDto mapToDto(StatsEntry entry) {
        StatsDto result = new StatsDto();
        result.setYear(entry.getYear());
        result.setMonth(entry.getMonth());
        result.setDay(entry.getDay());
        result.setType(entry.getType());
        result.setCount(entry.getCount());
        return result;
    }
}