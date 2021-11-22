package com.harvest.katadamienhuart.infrastructure.interfaces;

import com.harvest.katadamienhuart.domain.Sensor;
import com.harvest.katadamienhuart.domain.SensorState;

import java.time.ZonedDateTime;
import java.util.Objects;

public final class SensorDTO {

    public final SensorState sensorState;
    public final Integer sensedTemperature;
    public final ZonedDateTime sensedAt;

    public SensorDTO(final Sensor sensor) {
        this.sensorState = Objects.requireNonNull(sensor.sensorState());
        this.sensedTemperature = Objects.requireNonNull(sensor.sensedTemperature().temperature());
        this.sensedAt = sensor.sensedAt().at();
    }

}
