package com.smec.users.events;

import java.util.List;

import com.smec.users.events.EventService.IllegalReferenceException;

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
    public List<EventEntity> list() {
        return eventService.fetchAllEvents();
    }

    @PostMapping
    public EventEntity store(@RequestBody EventDto entity) throws Exception {
        try {
            return eventService.store(new EventEntity(entity.getType()), entity.getAccountId());
        } catch (IllegalReferenceException e) {
            throw new BadHttpRequest(e);
        }
    }
}