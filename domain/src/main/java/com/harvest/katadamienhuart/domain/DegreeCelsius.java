package com.harvest.katadamienhuart.domain;

import java.io.Serializable;
import java.util.Objects;

public record DegreeCelsius(Integer temperature) implements Serializable {

    public DegreeCelsius {
        Objects.requireNonNull(temperature);
    }

    public boolean isGreaterThanOrEquals(final DegreeCelsius degreeCelsius) {
        return temperature >= degreeCelsius.temperature;
    }

    public boolean isBeforeThan(final DegreeCelsius degreeCelsius) {
        return temperature < degreeCelsius.temperature;
    }

    public boolean isPositive() {
        return temperature > 0;
    }
}
