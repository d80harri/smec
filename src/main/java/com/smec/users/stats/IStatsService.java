package com.smec.users.stats;

import java.util.List;

import com.smec.users.exceptions.IllegalReferenceException;

public interface IStatsService {
    List<StatsEntry> fetchAllEvents();

    StatsEntry store(StatsEntry entity, int accountId) throws IllegalReferenceException;

}
