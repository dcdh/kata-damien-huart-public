package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public record WarmLimit(DegreeCelsius limit) implements Limit {

    public WarmLimit {
        Objects.requireNonNull(limit);
        if (!limit.isPositive()) {
            throw new WarmLimitMustBePositiveException(limit);
        }
    }
}
