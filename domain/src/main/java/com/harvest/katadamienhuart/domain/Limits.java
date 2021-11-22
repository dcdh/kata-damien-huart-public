package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public final class Limits {

    private final ColdLimit coldLimit;
    private final WarnLimit warnLimit;

    public Limits(final ColdLimit coldLimit,
                  final WarnLimit warnLimit) {
        this.coldLimit = Objects.requireNonNull(coldLimit);
        this.warnLimit = Objects.requireNonNull(warnLimit);
        if (warnLimit.limit().isBeforeThan(coldLimit.limit())) {
            throw new IllegalStateException("Warn limit could not be before cold limit");
        }
    }

    public ColdLimit coldLimit() {
        return coldLimit;
    }

    public WarnLimit warnLimit() {
        return warnLimit;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Limits)) return false;
        final Limits that = (Limits) o;
        return Objects.equals(coldLimit, that.coldLimit) &&
                Objects.equals(warnLimit, that.warnLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coldLimit, warnLimit);
    }
}
