package com.smec.users.stats;

import java.util.List;

public interface IStatsDao {
    List<StatsEntry> fetchAll(int accountId);

    StatsEntry store(StatsEntry entity);
}
