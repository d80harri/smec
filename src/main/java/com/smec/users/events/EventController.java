package com.smec.users.events;

import java.util.List;
import java.util.stream.Collectors;

import com.smec.users.exceptions.IllegalReferenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.tools.web.BadHttpRequest;

@RestController
@RequestMapping(value = "/events")
public class EventController {

    @Autowired
    private IEventService eventService;

    @GetMapping()
    public List<EventDto> list() { // TODO: should return DTO
        return eventService.fetchAllEvents().stream().map(EventController::mapToDto).collect(Collectors.toList());
    }

    @PostMapping // TODO: should return DTO
    public EventDto store(@RequestBody EventDto entity) throws Exception {
        try {
            return mapToDto(eventService.store(new EventEntity(entity.getType()), entity.getAccountId()));
        } catch (IllegalReferenceException e) {
            throw new BadHttpRequest(e);
        }
    }

    private static EventDto mapToDto(EventEntity store) {
        EventDto result = new EventDto(store.getType(), store.getAccount().getId());
        result.setId(store.getId());
        result.setTime(store.getTime());
        return result;
    }
}