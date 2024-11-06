package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public final class ColdLimitMustBePositiveException extends RuntimeException {

    private final DegreeCelsius limit;

    public ColdLimitMustBePositiveException(final DegreeCelsius limit) {
        this.limit = Objects.requireNonNull(limit);
    }

    public DegreeCelsius limit() {
        return limit;
    }

}
