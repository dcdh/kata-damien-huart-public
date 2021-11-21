package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public final class Sensor {

    private final SensorState sensorState;
    private final DegreeCelsius sensedTemperature;

    public Sensor(final DegreeCelsius sensedTemperature, final ColdThreshold coldThreshold, final WarnThreshold warnThreshold) {
        this.sensedTemperature = Objects.requireNonNull(sensedTemperature);
        this.sensorState = SensorState.fromSensedTemperature(sensedTemperature,
                Objects.requireNonNull(coldThreshold),
                Objects.requireNonNull(warnThreshold));
    }

    public DegreeCelsius sensedTemperature() {
        return sensedTemperature;
    }

    public SensorState sensorState() {
        return sensorState;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Sensor)) return false;
        final Sensor sensor = (Sensor) o;
        return sensorState == sensor.sensorState &&
                Objects.equals(sensedTemperature, sensor.sensedTemperature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorState, sensedTemperature);
    }
}
