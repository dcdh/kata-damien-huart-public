package com.harvest.katadamienhuart.domain;

import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsException;

import java.util.Objects;

public final class WarmLimitMustBePositiveException extends RedefineLimitsException {

    private final DegreeCelsius limit;

    public WarmLimitMustBePositiveException(final DegreeCelsius limit) {
        this.limit = Objects.requireNonNull(limit);
    }

    public DegreeCelsius limit() {
        return limit;
    }

}
