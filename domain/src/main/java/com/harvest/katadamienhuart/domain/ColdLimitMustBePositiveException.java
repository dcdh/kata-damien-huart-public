package com.harvest.katadamienhuart.domain;

import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsException;

import java.util.Objects;

public final class ColdLimitMustBePositiveException extends RedefineLimitsException {

    private final DegreeCelsius limit;

    public ColdLimitMustBePositiveException(final DegreeCelsius limit) {
        this.limit = Objects.requireNonNull(limit);
    }

    public DegreeCelsius limit() {
        return limit;
    }

}
