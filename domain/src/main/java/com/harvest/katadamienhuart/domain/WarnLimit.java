package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public final class WarnLimit {

    private final DegreeCelsius limit;

    public WarnLimit(final DegreeCelsius limit) {
        this.limit = Objects.requireNonNull(limit);
        if (!limit.isPositive()) {
            throw new WarnLimitMustBePositiveException(limit);
        }
    }

    public DegreeCelsius limit() {
        return limit;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof WarnLimit)) return false;
        final WarnLimit that = (WarnLimit) o;
        return Objects.equals(limit, that.limit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(limit);
    }
}
