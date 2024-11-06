package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public record ColdLimit(DegreeCelsius limit) implements Limit {

    public ColdLimit(DegreeCelsius limit) {
        this.limit = Objects.requireNonNull(limit);
        if (!limit.isPositive()) {
            throw new ColdLimitMustBePositiveException(limit);
        }
    }
}
