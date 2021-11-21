package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public final class WarnLimit {

    private final DegreeCelsius threshold;

    public WarnLimit(final DegreeCelsius threshold) {
        this.threshold = Objects.requireNonNull(threshold);
    }

    public DegreeCelsius threshold() {
        return threshold;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof WarnLimit)) return false;
        final WarnLimit that = (WarnLimit) o;
        return Objects.equals(threshold, that.threshold);
    }

    @Override
    public int hashCode() {
        return Objects.hash(threshold);
    }
}
