package com.smec.users.events;

import java.util.List;

public interface IEventDao {
    List<EventEntity> fetchAllAccounts();

    EventEntity store(EventEntity entity);

    int deleteOld(long l);
}
