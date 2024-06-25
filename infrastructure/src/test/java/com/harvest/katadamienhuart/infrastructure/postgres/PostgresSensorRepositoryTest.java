package com.harvest.katadamienhuart.infrastructure.postgres;

import com.harvest.katadamienhuart.domain.*;
import io.quarkus.narayana.jta.QuarkusTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@QuarkusTest
public class PostgresSensorRepositoryTest extends RepositoryTest {

    @Inject
    PostgresSensorRepository postgresSensorRepository;

    @Test
    public void should_store_sensor() {
        // Given
        final Sensor givenSensor = new Sensor(
                new TakenAt(ZonedDateTime.of(2021, 10, 19, 0, 0, 0, 0, ZoneOffset.UTC)),
                new TakenTemperature(new DegreeCelsius(20)), SensorState.COLD);

        // When
        final Sensor stored = postgresSensorRepository.store(givenSensor);

        // Then
        assertThat(stored).isEqualTo(new Sensor(
                new TakenAt(ZonedDateTime.of(2021, 10, 19, 0, 0, 0, 0, ZoneOffset.UTC)),
                new TakenTemperature(new DegreeCelsius(20)), SensorState.COLD));
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement selectItemsPreparedStatement = connection.prepareStatement("SELECT * FROM T_SENSOR")) {
            final ResultSet queryItemsResultSet = selectItemsPreparedStatement.executeQuery();
            queryItemsResultSet.next();
            assertAll(
                    () -> assertThat(queryItemsResultSet.getLong("id")).isEqualTo(1L),
                    () -> assertThat(queryItemsResultSet.getObject("takenat", OffsetDateTime.class))
                            .isEqualTo(OffsetDateTime.of(2021, 10, 19, 0, 0, 0, 0, ZoneOffset.UTC)),
                    () -> assertThat(queryItemsResultSet.getInt("takentemperature")).isEqualTo(20),
                    () -> assertThat(queryItemsResultSet.getString("sensorstate")).isEqualTo("COLD"),
                    () -> assertThat(queryItemsResultSet.next()).isFalse() // only one persisted
            );
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void should_get_last_15_ordered_by_sensed_at_desc() throws Exception {
        // Given
        QuarkusTransaction.requiringNew().run(() -> {
            for (int dayOfMonth = 1; dayOfMonth < 20; dayOfMonth++) {
                entityManager.persist(new SensorEntity(
                        new Sensor(
                                new TakenAt(ZonedDateTime.of(2021, 10, dayOfMonth, 0, 0, 0, 0, ZoneOffset.UTC)),
                                new TakenTemperature(new DegreeCelsius(20)),
                                SensorState.COLD)));
            }
        });

        // When
        final List<Sensor> sensors = postgresSensorRepository.getLast15OrderedByTakenAtDesc();

        // Then
        assertThat(sensors).containsExactly(
                new Sensor(
                        new TakenAt(ZonedDateTime.of(2021, 10, 19, 0, 0, 0, 0, ZoneOffset.UTC)),
                        new TakenTemperature(new DegreeCelsius(20)),
                        SensorState.COLD),
                new Sensor(
                        new TakenAt(ZonedDateTime.of(2021, 10, 18, 0, 0, 0, 0, ZoneOffset.UTC)),
                        new TakenTemperature(new DegreeCelsius(20)),
                        SensorState.COLD),
                new Sensor(
                        new TakenAt(ZonedDateTime.of(2021, 10, 17, 0, 0, 0, 0, ZoneOffset.UTC)),
                        new TakenTemperature(new DegreeCelsius(20)),
                        SensorState.COLD),
                new Sensor(
                        new TakenAt(ZonedDateTime.of(2021, 10, 16, 0, 0, 0, 0, ZoneOffset.UTC)),
                        new TakenTemperature(new DegreeCelsius(20)),
                        SensorState.COLD),
                new Sensor(
                        new TakenAt(ZonedDateTime.of(2021, 10, 15, 0, 0, 0, 0, ZoneOffset.UTC)),
                        new TakenTemperature(new DegreeCelsius(20)),
                        SensorState.COLD),
                new Sensor(
                        new TakenAt(ZonedDateTime.of(2021, 10, 14, 0, 0, 0, 0, ZoneOffset.UTC)),
                        new TakenTemperature(new DegreeCelsius(20)),
                        SensorState.COLD),
                new Sensor(
                        new TakenAt(ZonedDateTime.of(2021, 10, 13, 0, 0, 0, 0, ZoneOffset.UTC)),
                        new TakenTemperature(new DegreeCelsius(20)),
                        SensorState.COLD),
                new Sensor(
                        new TakenAt(ZonedDateTime.of(2021, 10, 12, 0, 0, 0, 0, ZoneOffset.UTC)),
                        new TakenTemperature(new DegreeCelsius(20)),
                        SensorState.COLD),
                new Sensor(
                        new TakenAt(ZonedDateTime.of(2021, 10, 11, 0, 0, 0, 0, ZoneOffset.UTC)),
                        new TakenTemperature(new DegreeCelsius(20)),
                        SensorState.COLD),
                new Sensor(
                        new TakenAt(ZonedDateTime.of(2021, 10, 10, 0, 0, 0, 0, ZoneOffset.UTC)),
                        new TakenTemperature(new DegreeCelsius(20)),
                        SensorState.COLD),
                new Sensor(
                        new TakenAt(ZonedDateTime.of(2021, 10, 9, 0, 0, 0, 0, ZoneOffset.UTC)),
                        new TakenTemperature(new DegreeCelsius(20)),
                        SensorState.COLD),
                new Sensor(
                        new TakenAt(ZonedDateTime.of(2021, 10, 8, 0, 0, 0, 0, ZoneOffset.UTC)),
                        new TakenTemperature(new DegreeCelsius(20)),
                        SensorState.COLD),
                new Sensor(
                        new TakenAt(ZonedDateTime.of(2021, 10, 7, 0, 0, 0, 0, ZoneOffset.UTC)),
                        new TakenTemperature(new DegreeCelsius(20)),
                        SensorState.COLD),
                new Sensor(
                        new TakenAt(ZonedDateTime.of(2021, 10, 6, 0, 0, 0, 0, ZoneOffset.UTC)),
                        new TakenTemperature(new DegreeCelsius(20)),
                        SensorState.COLD),
                new Sensor(
                        new TakenAt(ZonedDateTime.of(2021, 10, 5, 0, 0, 0, 0, ZoneOffset.UTC)),
                        new TakenTemperature(new DegreeCelsius(20)),
                        SensorState.COLD));
    }

}
