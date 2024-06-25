package com.harvest.katadamienhuart.infrastructure.postgres;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class RepositoryTest {

    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE T_LIMITS, T_SENSOR";
    public static final String RESET_T_LIMIT_SEQ = "SELECT setval('t_limits_seq', 1, false);";
    public static final String RESET_T_SENSOR_SEQ = "SELECT setval('t_sensor_seq', 1, false);";

    @Inject
    EntityManager entityManager;

    @Inject
    DataSource dataSource;

    @BeforeEach
    @AfterEach
    public void flush() {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement truncatePreparedStatement = connection.prepareStatement(TRUNCATE_TABLE);
             final PreparedStatement resetLimitSeq = connection.prepareStatement(RESET_T_LIMIT_SEQ);
             final PreparedStatement resetSensorSeq = connection.prepareStatement(RESET_T_SENSOR_SEQ)) {
            truncatePreparedStatement.execute();
            resetLimitSeq.execute();
            resetSensorSeq.execute();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
