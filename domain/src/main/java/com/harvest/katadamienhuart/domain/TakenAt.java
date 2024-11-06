package com.harvest.katadamienhuart.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

public record TakenAt(ZonedDateTime at) implements Comparable<TakenAt>, Serializable {

    public TakenAt {
        Objects.requireNonNull(at);
    }

    @Override
    public int compareTo(final TakenAt other) {
        return this.at.compareTo(other.at);
    }
}
