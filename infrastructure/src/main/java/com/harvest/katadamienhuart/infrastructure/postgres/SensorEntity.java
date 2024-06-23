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
    public ZonedDateTime sensedAt;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    public SensorState sensorState;

    @NotNull
    public Integer sensedTemperature;

    public SensorEntity() {}

    public SensorEntity(final Sensor sensor) {
        this.sensedAt = sensor.sensedAt().at();
        this.sensorState = sensor.sensorState();
        this.sensedTemperature = sensor.sensedTemperature().temperature();
    }

    public Sensor toSensor() {
        return new Sensor(
                new SensedAt(sensedAt.withZoneSameInstant(ZoneOffset.UTC)),
                new DegreeCelsius(sensedTemperature),
                sensorState);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SensorEntity)) return false;
        final SensorEntity that = (SensorEntity) o;
        return Objects.equals(sensedAt, that.sensedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensedAt);
    }
}
