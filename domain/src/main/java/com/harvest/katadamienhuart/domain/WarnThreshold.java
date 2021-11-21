package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public final class WarnThreshold {

    private final DegreeCelsius threshold;

    public WarnThreshold(final DegreeCelsius threshold) {
        this.threshold = Objects.requireNonNull(threshold);
    }

    public DegreeCelsius threshold() {
        return threshold;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof WarnThreshold)) return false;
        final WarnThreshold that = (WarnThreshold) o;
        return Objects.equals(threshold, that.threshold);
    }

    @Override
    public int hashCode() {
        return Objects.hash(threshold);
    }
}
