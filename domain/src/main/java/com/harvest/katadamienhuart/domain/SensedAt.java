package com.harvest.katadamienhuart.domain;

import java.time.ZonedDateTime;
import java.util.Objects;

public final class SensedAt {

    private final ZonedDateTime at;

    public SensedAt(final ZonedDateTime at) {
        this.at = Objects.requireNonNull(at);
    }

    public ZonedDateTime at() {
        return at;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof SensedAt)) return false;
        final SensedAt sensedAt = (SensedAt) o;
        return Objects.equals(at, sensedAt.at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(at);
    }

    @Override
    public String toString() {
        return "SensedAt{" +
                "at=" + at +
                '}';
    }
}
