package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public class WarmLimitMustBeSuperiorToColdLimitException extends RuntimeException {

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
