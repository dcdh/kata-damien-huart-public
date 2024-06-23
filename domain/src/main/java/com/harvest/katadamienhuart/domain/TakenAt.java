package com.harvest.katadamienhuart.domain;

import java.time.ZonedDateTime;
import java.util.Objects;

public record TakenAt(ZonedDateTime at) {

    public TakenAt {
        Objects.requireNonNull(at);
    }
}
