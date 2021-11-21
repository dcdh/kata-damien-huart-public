package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public final class Sensor {

    private final SensorState sensorState;
    private final DegreeCelsius sensedTemperature;
    private final SensedAt sensedAt;

    public Sensor(final SensedAt sensedAt, final DegreeCelsius sensedTemperature, final Thresholds thresholds) {
        this.sensedAt = Objects.requireNonNull(sensedAt);
        this.sensedTemperature = Objects.requireNonNull(sensedTemperature);
        this.sensorState = SensorState.fromSensedTemperature(sensedTemperature,
                Objects.requireNonNull(thresholds));
    }

    public SensedAt sensedAt() {
        return sensedAt;
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
                Objects.equals(sensedTemperature, sensor.sensedTemperature) &&
                Objects.equals(sensedAt, sensor.sensedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorState, sensedTemperature, sensedAt);
    }
}
