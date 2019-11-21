package com.mehehe.dao;

import com.mehehe.model.BikeHistory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class BikeHistoryMapper implements ResultSetMapper<BikeHistory> {
    @Override
    public BikeHistory map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        Timestamp creation_date = resultSet.getTimestamp("CREATION_DATE");
        return new BikeHistory(resultSet.getInt("BIKE_NUMBER"), resultSet.getInt("STATION_NUMBER"), creation_date.toLocalDateTime());
    }
}
