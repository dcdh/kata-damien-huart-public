package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public record SensorHistory(TakenTemperature takenTemperature, SensorState sensorState) {
    public SensorHistory {
        Objects.requireNonNull(takenTemperature);
        Objects.requireNonNull(sensorState);
    }
}
