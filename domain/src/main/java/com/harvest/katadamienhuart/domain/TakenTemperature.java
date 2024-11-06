package com.harvest.katadamienhuart.domain;

import java.io.Serializable;
import java.util.Objects;

public record TakenTemperature(Temperature temperature,
                               TakenAt takenAt) implements Comparable<TakenTemperature>, Serializable {
    public TakenTemperature {
        Objects.requireNonNull(temperature);
        Objects.requireNonNull(takenAt);
    }

    public boolean isBeforeThan(final DegreeCelsius limit) {
        return temperature.isBeforeThan(limit);
    }

    public boolean isGreaterThanOrEquals(final DegreeCelsius limit) {
        return temperature.isGreaterThanOrEquals(limit);
    }

    @Override
    public int compareTo(final TakenTemperature other) {
        return this.takenAt.compareTo(other.takenAt);
    }
}
