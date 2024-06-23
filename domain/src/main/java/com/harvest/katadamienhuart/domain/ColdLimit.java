package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public record ColdLimit(DegreeCelsius limit) {

    public ColdLimit {
        Objects.requireNonNull(limit);
        if (!limit.isPositive()) {
            throw new ColdLimitMustBePositiveException(limit);
        }
    }

}
