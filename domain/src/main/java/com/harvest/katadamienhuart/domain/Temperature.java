package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public record Temperature(DegreeCelsius degreeCelsius) {
    public Temperature {
        Objects.requireNonNull(degreeCelsius);
    }

    public boolean isBeforeThan(final DegreeCelsius limit) {
        return degreeCelsius.isBeforeThan(limit);
    }

    public boolean isGreaterThanOrEquals(final DegreeCelsius limit) {
        return degreeCelsius.isGreaterThanOrEquals(limit);
    }
}
