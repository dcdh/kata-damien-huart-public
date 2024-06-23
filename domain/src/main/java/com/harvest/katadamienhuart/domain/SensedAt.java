package com.harvest.katadamienhuart.domain;

import java.time.ZonedDateTime;
import java.util.Objects;

public record SensedAt(ZonedDateTime at) {

    public SensedAt {
        Objects.requireNonNull(at);
    }
}
