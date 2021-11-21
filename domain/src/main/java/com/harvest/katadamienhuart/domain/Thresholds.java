package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public final class Thresholds {

    private final ColdThreshold coldThreshold;
    private final WarnThreshold warnThreshold;

    public Thresholds(final ColdThreshold coldThreshold,
                      final WarnThreshold warnThreshold) {
        this.coldThreshold = Objects.requireNonNull(coldThreshold);
        this.warnThreshold = Objects.requireNonNull(warnThreshold);
        if (warnThreshold.threshold().isBeforeThan(coldThreshold.threshold())) {
            throw new IllegalStateException("Warn threshold could not be before cold threshold");
        }
    }

    public ColdThreshold coldThreshold() {
        return coldThreshold;
    }

    public WarnThreshold warnThreshold() {
        return warnThreshold;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Thresholds)) return false;
        final Thresholds that = (Thresholds) o;
        return Objects.equals(coldThreshold, that.coldThreshold) &&
                Objects.equals(warnThreshold, that.warnThreshold);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coldThreshold, warnThreshold);
    }
}
