package com.harvest.katadamienhuart.infrastructure.api;

import com.harvest.katadamienhuart.domain.Sensor;
import com.harvest.katadamienhuart.domain.SensorState;

import java.time.ZonedDateTime;
import java.util.Objects;

public record SensorDTO(SensorState sensorState, Integer sensedTemperature, ZonedDateTime sensedAt) {
    public SensorDTO {
        Objects.requireNonNull(sensorState);
        Objects.requireNonNull(sensedTemperature);
        Objects.requireNonNull(sensedAt);
    }

    public SensorDTO(final Sensor sensor) {
        this(sensor.sensorState(),
                sensor.sensedTemperature().temperature(),
                sensor.sensedAt().at());
    }

}
