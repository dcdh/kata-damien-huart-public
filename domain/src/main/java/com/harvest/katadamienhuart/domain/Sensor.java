package com.harvest.katadamienhuart.domain;

import java.io.Serializable;
import java.util.Objects;

public record Sensor(TakenTemperature takenTemperature, Limits limits) implements Serializable {

    public Sensor {
        Objects.requireNonNull(takenTemperature);
        Objects.requireNonNull(limits);
    }

    public SensorState sensorState() {
        return SensorState.fromSensedTemperature(takenTemperature, limits);
    }
}
