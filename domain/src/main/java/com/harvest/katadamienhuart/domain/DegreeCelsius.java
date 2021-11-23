package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public final class DegreeCelsius {

    private final Integer temperature;

    public DegreeCelsius(final Integer temperature) {
        this.temperature = Objects.requireNonNull(temperature);
    }

    public boolean isGreaterThanOrEquals(final DegreeCelsius degreeCelsius) {
        return temperature >= degreeCelsius.temperature;
    }

    public boolean isBeforeThan(final DegreeCelsius degreeCelsius) {
        return temperature < degreeCelsius.temperature;
    }

    public Integer temperature() {
        return temperature;
    }

    public boolean isPositive() {
        return temperature > 0;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof DegreeCelsius)) return false;
        final DegreeCelsius that = (DegreeCelsius) o;
        return Objects.equals(temperature, that.temperature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature);
    }
}
