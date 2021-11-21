package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public final class DegreeCelsius {

    private final Long temperature;

    public DegreeCelsius(final Long temperature) {
        this.temperature = Objects.requireNonNull(temperature);
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
