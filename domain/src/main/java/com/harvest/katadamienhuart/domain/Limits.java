package com.harvest.katadamienhuart.domain;

import java.util.Objects;

public record Limits(ColdLimit coldLimit, WarmLimit warmLimit) {

    public Limits {
        Objects.requireNonNull(coldLimit);
        Objects.requireNonNull(warmLimit);
        if (warmLimit.limit().isBeforeThan(coldLimit.limit())) {
            throw new WarmLimitMustBeSuperiorToColdLimitException(coldLimit, warmLimit);
        }
    }
}
