package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public record Sensor(SensedAt sensedAt, DegreeCelsius sensedTemperature, SensorState sensorState) {

    public Sensor(final SensedAt sensedAt, final DegreeCelsius sensedTemperature, final Limits limits) {
        this(sensedAt, sensedTemperature,
                SensorState.fromSensedTemperature(sensedTemperature, Objects.requireNonNull(limits)));
    }

    public Sensor {
        Objects.requireNonNull(sensedAt);
        Objects.requireNonNull(sensedTemperature);
        Objects.requireNonNull(sensorState);
    }
}
