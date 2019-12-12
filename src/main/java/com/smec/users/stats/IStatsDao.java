package com.smec.users.stats;

import java.util.List;

public interface IStatsDao {
    List<StatsEntry> fetchAllAccounts();

    StatsEntry store(StatsEntry entity);
}
