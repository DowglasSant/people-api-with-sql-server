package com.santana.dowglas.sql_server_application.infrastructure.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

@Component
public class SqlServerHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    public SqlServerHealthIndicator(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Health health() {
        try (Connection conn = dataSource.getConnection()) {
            if (conn.isValid(2)) {
                return Health.up().withDetail("SQL Server", "Available").build();
            } else {
                return Health.down().withDetail("SQL Server", "Not responding").build();
            }
        } catch (SQLException e) {
            return Health.down(e).withDetail("SQL Server", "Connection failed").build();
        }
    }
}