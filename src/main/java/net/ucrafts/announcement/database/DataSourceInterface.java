package net.ucrafts.announcement.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DataSourceInterface {


    void close();

    Connection getConnection() throws SQLException;

    void execute(final PreparedStatement preparedStatement);
}
