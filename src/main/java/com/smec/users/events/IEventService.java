package com.smec.users.events;

import java.util.List;

import com.smec.users.events.EventService.IllegalReferenceException;

public interface IEventService {
    List<EventEntity> fetchAllEvents();

    EventEntity store(EventEntity entity, int accountId) throws IllegalReferenceException;

    int deleteOld(long l);
}
