package com.ordermanagement;

import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import jakarta.inject.Singleton;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Singleton
@Context
@Requires(notEnv = Environment.TEST)
public class DatabaseCreator {

    private final DataSource dataSource;

    public DatabaseCreator(DataSource dataSource) {
        this.dataSource = dataSource;
        createDatabase();
    }

    private void createDatabase() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE DATABASE IF NOT EXISTS order_management");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create database", e);
        }
    }
}
