package com.harvest.katadamienhuart.infrastructure.postgres;

import com.harvest.katadamienhuart.domain.ColdLimit;
import com.harvest.katadamienhuart.domain.DegreeCelsius;
import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.WarmLimit;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@QuarkusTest
public class PostgresLimitsRepositoryTest extends RepositoryTest {

    @Inject
    PostgresLimitsRepository postgresLimitsRepository;

    @Test
    public void should_return_null_when_limits_have_not_been_defined() {
        assertThat(postgresLimitsRepository.getLastLimits()).isNull();
    }

    @Test
    public void should_store_limits() {
        // Given
        final Limits givenLimits = new Limits(
                new ColdLimit(new DegreeCelsius(20)),
                new WarmLimit(new DegreeCelsius(40)));

        // When
        final Limits stored = postgresLimitsRepository.store(givenLimits);

        // Then
        assertThat(stored).isEqualTo(new Limits(
                new ColdLimit(new DegreeCelsius(20)),
                new WarmLimit(new DegreeCelsius(40))));
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement selectItemsPreparedStatement = connection.prepareStatement("SELECT * FROM T_LIMITS")) {
            final ResultSet queryItemsResultSet = selectItemsPreparedStatement.executeQuery();
            queryItemsResultSet.next();
            assertAll(
                    () -> assertThat(queryItemsResultSet.getLong("id")).isEqualTo(1L),
                    () -> assertThat(queryItemsResultSet.getInt("coldlimit")).isEqualTo(20),
                    () -> assertThat(queryItemsResultSet.getInt("warmlimit")).isEqualTo(40),
                    () -> assertThat(queryItemsResultSet.next()).isFalse() // only one persisted
            );
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void should_get_last_limits() {
        // Given
        QuarkusTransaction.requiringNew().run(() -> {
            entityManager.persist(new LimitsEntity(new Limits(
                    new ColdLimit(new DegreeCelsius(20)),
                    new WarmLimit(new DegreeCelsius(40)))));
            entityManager.persist(new LimitsEntity(new Limits(
                    new ColdLimit(new DegreeCelsius(22)),
                    new WarmLimit(new DegreeCelsius(42)))));
        });

        // When
        final Limits limits = postgresLimitsRepository.getLastLimits();

        // Then
        assertThat(limits).isEqualTo(new Limits(
                new ColdLimit(new DegreeCelsius(22)),
                new WarmLimit(new DegreeCelsius(42))));
    }

}
