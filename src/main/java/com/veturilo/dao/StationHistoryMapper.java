package com.veturilo.dao;

import com.veturilo.model.Station;
import com.veturilo.model.StationHistory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class StationHistoryMapper implements ResultSetMapper<StationHistory> {
    @Override
    public StationHistory map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        Timestamp creation_date = resultSet.getTimestamp("CREATION_DATE");
        Station station = new Station(resultSet.getInt("STATION_NUMBER"), resultSet.getInt("BIKES"), resultSet.getInt("BIKE_RACKS"), resultSet.getInt("FREE_RACKS"));
        return new StationHistory(station, creation_date.toLocalDateTime());
    }
}
