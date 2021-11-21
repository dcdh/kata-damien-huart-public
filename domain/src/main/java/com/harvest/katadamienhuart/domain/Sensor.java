package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public final class Sensor {

    private final SensorState sensorState;

    public Sensor(final DegreeCelsius sensedTemperature) {
        this.sensorState = SensorState.fromSensedTemperature(sensedTemperature);
    }

    public SensorState sensorState() {
        return sensorState;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Sensor)) return false;
        final Sensor sensor = (Sensor) o;
        return sensorState == sensor.sensorState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorState);
    }
}
