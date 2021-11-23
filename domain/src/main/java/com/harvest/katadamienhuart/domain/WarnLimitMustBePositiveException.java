package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public class WarnLimitMustBePositiveException extends RuntimeException {

    private final DegreeCelsius limit;

    public WarnLimitMustBePositiveException(final DegreeCelsius limit) {
        this.limit = Objects.requireNonNull(limit);
    }

    public DegreeCelsius limit() {
        return limit;
    }

}
