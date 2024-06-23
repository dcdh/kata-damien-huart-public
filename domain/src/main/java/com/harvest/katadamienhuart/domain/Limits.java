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

    public static Limits ofDefault() {
        return new Limits(new ColdLimit(new DegreeCelsius(22)),
                new WarmLimit(new DegreeCelsius(40)));
    }
}
