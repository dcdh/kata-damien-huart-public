package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public final class ColdLimit {

    private final DegreeCelsius limit;

    public ColdLimit(final DegreeCelsius limit) {
        this.limit = Objects.requireNonNull(limit);
        if (!limit.isPositive()) {
            throw new ColdLimitMustBePositiveException(limit);
        }
    }

    public DegreeCelsius limit() {
        return limit;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof ColdLimit)) return false;
        final ColdLimit that = (ColdLimit) o;
        return Objects.equals(limit, that.limit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(limit);
    }
}
