package com.harvest.katadamienhuart.domain;

import java.io.Serializable;
import java.util.Objects;

public record ColdLimit(DegreeCelsius limit) implements Limit, Serializable {

    public ColdLimit(DegreeCelsius limit) {
        this.limit = Objects.requireNonNull(limit);
        if (!limit.isPositive()) {
            throw new ColdLimitMustBePositiveException(limit);
        }
    }
}
