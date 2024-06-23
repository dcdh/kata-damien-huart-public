package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public record TakenTemperature(DegreeCelsius temperature) {
    public TakenTemperature {
        Objects.requireNonNull(temperature);
    }

    public boolean isBeforeThan(final DegreeCelsius limit) {
        return temperature.isBeforeThan(limit);
    }

    public boolean isGreaterThanOrEquals(final DegreeCelsius limit) {
        return temperature.isGreaterThanOrEquals(limit);
    }
}
