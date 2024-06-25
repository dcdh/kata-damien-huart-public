package com.harvest.katadamienhuart.infrastructure.postgres;

import com.harvest.katadamienhuart.domain.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
@Table(name = "T_SENSOR")
@SequenceGenerator(
        name = "t_sensor_seq",
        sequenceName = "t_sensor_seq",
        initialValue = 1,
        allocationSize = 1 // Disable sequence cache
)
public class SensorEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_sensor_seq")
    private Integer id;

    @NotNull
    public ZonedDateTime takenAt;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    public SensorState sensorState;

    @NotNull
    public Integer takenTemperature;

    public SensorEntity() {
    }

    public SensorEntity(final Sensor sensor) {
        this.takenAt = sensor.takenAt().at();
        this.sensorState = sensor.sensorState();
        this.takenTemperature = sensor.takenTemperature().temperature().temperature();
    }

    public Sensor toSensor() {
        return new Sensor(
                new TakenAt(takenAt.withZoneSameInstant(ZoneOffset.UTC)),
                new TakenTemperature(new DegreeCelsius(takenTemperature)),
                sensorState);
    }

    public static Stream<SensorEntity> getLast15OrderByTakenAtDesc() {
        return findAll(Sort.descending("takenAt")).page(0, 15).stream();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SensorEntity)) return false;
        final SensorEntity that = (SensorEntity) o;
        return Objects.equals(takenAt, that.takenAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(takenAt);
    }
}
