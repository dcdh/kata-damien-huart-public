package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public record Sensor(TakenAt takenAt, TakenTemperature takenTemperature, SensorState sensorState) {

    public Sensor(final TakenAt takenAt, final TakenTemperature takenTemperature, final Limits limits) {
        this(takenAt, takenTemperature,
                SensorState.fromSensedTemperature(takenTemperature, Objects.requireNonNull(limits)));
    }

    public Sensor {
        Objects.requireNonNull(takenAt);
        Objects.requireNonNull(takenTemperature);
        Objects.requireNonNull(sensorState);
    }
}
