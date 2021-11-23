package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public final class WarmLimit {

    private final DegreeCelsius limit;

    public WarmLimit(final DegreeCelsius limit) {
        this.limit = Objects.requireNonNull(limit);
        if (!limit.isPositive()) {
            throw new WarmLimitMustBePositiveException(limit);
        }
    }

    public DegreeCelsius limit() {
        return limit;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof WarmLimit)) return false;
        final WarmLimit that = (WarmLimit) o;
        return Objects.equals(limit, that.limit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(limit);
    }
}
