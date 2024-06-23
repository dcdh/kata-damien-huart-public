package com.harvest.katadamienhuart.infrastructure.api;

import com.harvest.katadamienhuart.domain.Sensor;
import com.harvest.katadamienhuart.domain.SensorState;

import java.time.ZonedDateTime;
import java.util.Objects;

public record SensorDTO(SensorState sensorState, Integer takenTemperature, ZonedDateTime takenAt) {
    public SensorDTO {
        Objects.requireNonNull(sensorState);
        Objects.requireNonNull(takenTemperature);
        Objects.requireNonNull(takenAt);
    }

    public SensorDTO(final Sensor sensor) {
        this(sensor.sensorState(),
                sensor.takenTemperature().temperature().temperature(),
                sensor.takenAt().at());
    }

}
