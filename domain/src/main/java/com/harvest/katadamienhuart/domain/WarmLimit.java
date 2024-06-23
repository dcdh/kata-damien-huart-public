package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public record WarmLimit(DegreeCelsius limit) {

    public WarmLimit {
        Objects.requireNonNull(limit);
        if (!limit.isPositive()) {
            throw new WarmLimitMustBePositiveException(limit);
        }
    }
}
