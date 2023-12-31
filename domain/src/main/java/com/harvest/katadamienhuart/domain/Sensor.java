package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public final class Sensor {

    private final SensorState sensorState;
    private final DegreeCelsius sensedTemperature;
    private final SensedAt sensedAt;

    public Sensor(final SensedAt sensedAt, final DegreeCelsius sensedTemperature, final Limits limits) {
        this(sensedAt, sensedTemperature,
                SensorState.fromSensedTemperature(sensedTemperature, Objects.requireNonNull(limits)));
    }

    public Sensor(final SensedAt sensedAt, final DegreeCelsius sensedTemperature, final SensorState sensorState) {
        this.sensedAt = Objects.requireNonNull(sensedAt);
        this.sensedTemperature = Objects.requireNonNull(sensedTemperature);
        this.sensorState = Objects.requireNonNull(sensorState);
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

    @Override
    public String toString() {
        return "Sensor{" +
                "sensorState=" + sensorState +
                ", sensedTemperature=" + sensedTemperature +
                ", sensedAt=" + sensedAt +
                '}';
    }
}
