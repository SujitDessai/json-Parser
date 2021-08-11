package com.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Stream;

public class HSQLDBUtil {
    private static final String CREATE_TABLE_SQL = "CREATE TABLE events ( id varchar(20), duration varchar(10), type varchar(20), host varchar(20), alert varchar(10));";

    private static final String INSERT_USERS_SQL = "INSERT INTO events  (id, duration, type, host, alert) VALUES  (?, ?, ?, ?, ?);";

    public void createTable()  {

        System.out.println(CREATE_TABLE_SQL);
        // Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();
             // Step 2:Create a statement using connection object
             Statement statement = connection.createStatement();) {

            // Step 3: Execute the query or update query
            statement.execute(CREATE_TABLE_SQL);
        } catch (SQLException e) {
            // print SQL exception information
            JDBCUtils.printSQLException(e);
        }
    }

    public void insertRecord(String id, String duration,String type, String host, String alert)  {
        System.out.println(INSERT_USERS_SQL);
        // Step 1: Establishing a Connection
        try (Connection connection = JDBCUtils.getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, duration);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, host);
            preparedStatement.setString(5, alert);

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

            // print SQL exception information
            JDBCUtils.printSQLException(e);
        }

    }
}
