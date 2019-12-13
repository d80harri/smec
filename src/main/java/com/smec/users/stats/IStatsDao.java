package com.smec.users.stats;

import java.util.List;

public interface IStatsDao {
    List<StatsEntry> fetchAll(int accountId);
    StatsEntry fetchByGroup(int year, int month, int day, String type, int accountId);
    StatsEntry store(StatsEntry entity);
}
