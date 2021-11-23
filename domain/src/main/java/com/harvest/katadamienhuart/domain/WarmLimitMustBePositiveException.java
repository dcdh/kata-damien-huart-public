package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public class WarmLimitMustBePositiveException extends RuntimeException {

    private final DegreeCelsius limit;

    public WarmLimitMustBePositiveException(final DegreeCelsius limit) {
        this.limit = Objects.requireNonNull(limit);
    }

    public DegreeCelsius limit() {
        return limit;
    }

}
