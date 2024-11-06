package com.harvest.katadamienhuart.domain;

import java.io.Serializable;
import java.util.Objects;

public record SensorHistory(TakenTemperature takenTemperature, SensorState sensorState) implements Serializable {
    public SensorHistory {
        Objects.requireNonNull(takenTemperature);
        Objects.requireNonNull(sensorState);
    }
}
