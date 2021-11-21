package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public final class ColdThreshold {

    private final DegreeCelsius threshold;

    public ColdThreshold(final DegreeCelsius threshold) {
        this.threshold = Objects.requireNonNull(threshold);
    }

    public DegreeCelsius threshold() {
        return threshold;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ColdThreshold)) return false;
        final ColdThreshold that = (ColdThreshold) o;
        return Objects.equals(threshold, that.threshold);
    }

    @Override
    public int hashCode() {
        return Objects.hash(threshold);
    }
}
