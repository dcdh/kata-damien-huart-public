package com.harvest.katadamienhuart.domain;

import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsException;

import java.util.Objects;

public final class WarmLimitMustBeSuperiorToColdLimitException extends RedefineLimitsException {

    private final ColdLimit coldLimit;
    private final WarmLimit warmLimit;

    public WarmLimitMustBeSuperiorToColdLimitException(final ColdLimit coldLimit,
                                                       final WarmLimit warmLimit) {
        this.coldLimit = Objects.requireNonNull(coldLimit);
        this.warmLimit = Objects.requireNonNull(warmLimit);
    }

    public ColdLimit coldLimit() {
        return coldLimit;
    }

    public WarmLimit warmLimit() {
        return warmLimit;
    }
}
