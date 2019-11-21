package com.mehehe.dao;

import com.mehehe.model.BikeHistory;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface BikeHistoryDAO {
    String ORDER_BY_CREATION_DATE = " ORDER BY CREATION_DATE";
    String ORDER_BY_BIKE_NUMBER = " ORDER BY BIKE_NUMBER";

    @SqlUpdate("insert into BIKE_HISTORY (STATION_NUMBER, BIKE_NUMBER, CREATION_DATE) values (:stationNumber, :bikeNumber, :creationDate)")
    void insert(@Bind("creationDate") LocalDateTime creationDate, @Bind("stationNumber") int stationNumber, @Bind("bikeNumber") int bikeNumber);

    default void insert(BikeHistory bikeHistory) {
        insert(bikeHistory.getCreationDate(), bikeHistory.getStationNumber(), bikeHistory.getBikeNumber());
    }

    default void insert(Collection<BikeHistory> bikeHistories) {
        bikeHistories.forEach(this::insert);
    }

    @Mapper(BikeHistoryMapper.class)
    @SqlQuery("select STATION_NUMBER, CREATION_DATE, BIKE_NUMBER from BIKE_HISTORY where BIKE_NUMBER = :number and CREATION_DATE = :creationDate" + ORDER_BY_CREATION_DATE)
    List<BikeHistory> findByNumberAndDate(@Bind("number") int number, @Bind("creationDate") Timestamp creationDate);

    @Mapper(BikeHistoryMapper.class)
    @SqlQuery("select STATION_NUMBER, CREATION_DATE, BIKE_NUMBER from BIKE_HISTORY where BIKE_NUMBER = :number" + ORDER_BY_CREATION_DATE)
    List<BikeHistory> findByNumber(@Bind("number") int number);

    @Mapper(BikeHistoryMapper.class)
    @SqlQuery("select STATION_NUMBER, CREATION_DATE, BIKE_NUMBER from BIKE_HISTORY where STATION_NUMBER = :number" + ORDER_BY_BIKE_NUMBER)
    List<BikeHistory> findByStationNumber(@Bind("number") int number);
}
