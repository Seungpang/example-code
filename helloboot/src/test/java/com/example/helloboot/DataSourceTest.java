package com.example.helloboot;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

@JdbcTest
class DataSourceTest {
    @Autowired
    DataSource dataSource;

    @Test
    void connect() throws SQLException {
        final Connection connection = dataSource.getConnection();
        connection.close();
    }
}
