package com.mehehe.dao;

import com.mehehe.model.Station;
import com.mehehe.model.StationHistory;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface StationHistoryDAO {
    @SqlUpdate("insert into STATION_HISTORY (STATION_NUMBER, BIKES, BIKE_RACKS, FREE_RACKS, CREATION_DATE)"
        + " values (:stationNumber, :bikes, :bikeRacks, :freeRacks, :creationDate)")
    void insert(@Bind("creationDate") LocalDateTime creationDate, @Bind("stationNumber") int stationNumber,
                @Bind("bikes") int bikes, @Bind("bikeRacks") int bikeRacks,
                @Bind("freeRacks") int freeRacks);

    default void insert(StationHistory stationHistory) {
        Station station = stationHistory.getStation();
        insert(stationHistory.getCreationDate(), station.number, station.bikes, station.bikeRacks, station.freeRacks);
    }

    default void insert(Collection<StationHistory> stationHistories) {
        stationHistories.forEach(this::insert);
    }

    @Mapper(StationHistoryMapper.class)
    @SqlQuery("select STATION_NUMBER, CREATION_DATE, BIKES, BIKE_RACKS, FREE_RACKS from STATION_HISTORY where STATION_NUMBER = :number ORDER BY CREATION_DATE")
    List<StationHistory> findByNumber(@Bind("number") int number);
}
