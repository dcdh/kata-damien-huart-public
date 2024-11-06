package com.harvest.katadamienhuart.domain;

import java.io.Serializable;
import java.util.Objects;

public record WarmLimit(DegreeCelsius limit) implements Limit, Serializable {

    public WarmLimit {
        Objects.requireNonNull(limit);
        if (!limit.isPositive()) {
            throw new WarmLimitMustBePositiveException(limit);
        }
    }
}
