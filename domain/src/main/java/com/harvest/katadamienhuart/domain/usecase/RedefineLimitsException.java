package com.harvest.katadamienhuart.domain.usecase;

public final class RedefineLimitsException extends Exception {
    public RedefineLimitsException(final Exception exception) {
        super(exception);
    }
}
