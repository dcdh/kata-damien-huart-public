package com.harvest.katadamienhuart.infrastructure.postgres;

import com.harvest.katadamienhuart.domain.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "T_SENSOR")
public class SensorEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @NotNull
    public ZonedDateTime takenAt;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    public SensorState sensorState;

    @NotNull
    public Integer takenTemperature;

    public SensorEntity() {}

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
