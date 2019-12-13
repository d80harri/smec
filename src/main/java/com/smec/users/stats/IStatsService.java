package com.smec.users.stats;

import java.util.Date;
import java.util.List;

public interface IStatsService {
    List<StatsEntry> fetchAll(int accountId);

    void archivateOldEvents(Date date);

    StatsEntry getByGroup(Date time, String type, int id);
}
