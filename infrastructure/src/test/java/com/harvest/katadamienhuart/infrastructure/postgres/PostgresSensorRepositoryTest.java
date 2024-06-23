package com.harvest.katadamienhuart.infrastructure.postgres;

import com.harvest.katadamienhuart.domain.*;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@ExtendWith(MockitoExtension.class)
public class PostgresSensorRepositoryTest extends RepositoryTest {

    private static final String COUNT_SENSOR_SQL = "SELECT COUNT(*) FROM T_SENSOR";

    @Inject
    PostgresSensorRepository postgresSensorRepository;

    @Test
    public void should_save_sensor() {
        // Given

        // When
        postgresSensorRepository.save(new Sensor(
                new TakenAt(ZonedDateTime.now()), new TakenTemperature(new DegreeCelsius(20)), SensorState.COLD));

        // Then
        assertThat(((Number) entityManager.createNativeQuery(COUNT_SENSOR_SQL).getSingleResult()).longValue()).isEqualTo(1l);
    }

    @Test
    public void should_get_last_15_ordered_by_sensed_at_desc() throws Exception {
        // Given
        runInTransaction(() -> {
            for (int dayOfMonth = 1; dayOfMonth < 20; dayOfMonth++) {
                entityManager.persist(new SensorEntity(
                        new Sensor(
                                new TakenAt(ZonedDateTime.of(2021, 10, dayOfMonth, 0, 0, 0, 0, ZoneOffset.UTC)),
                                new TakenTemperature(new DegreeCelsius(20)),
                                SensorState.COLD)));
            }
            return null;
        });

        // When
        final List<Sensor> sensors = postgresSensorRepository.getLast15OrderedBySensedAtDesc();

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
