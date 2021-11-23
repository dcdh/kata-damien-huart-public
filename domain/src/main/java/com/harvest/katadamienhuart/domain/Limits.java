package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public final class Limits {

    private final ColdLimit coldLimit;
    private final WarmLimit warmLimit;

    public Limits(final ColdLimit coldLimit,
                  final WarmLimit warmLimit) {
        this.coldLimit = Objects.requireNonNull(coldLimit);
        this.warmLimit = Objects.requireNonNull(warmLimit);
        if (warmLimit.limit().isBeforeThan(coldLimit.limit())) {
            throw new IllegalStateException("Warm limit could not be before cold limit");
        }
    }

    public ColdLimit coldLimit() {
        return coldLimit;
    }

    public WarmLimit warmLimit() {
        return warmLimit;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Limits)) return false;
        final Limits that = (Limits) o;
        return Objects.equals(coldLimit, that.coldLimit) &&
                Objects.equals(warmLimit, that.warmLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coldLimit, warmLimit);
    }
}
