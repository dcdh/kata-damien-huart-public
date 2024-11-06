package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.DegreeCelsius;
import com.harvest.katadamienhuart.domain.Limit;
import com.harvest.katadamienhuart.domain.WarmLimit;

import java.io.Serializable;
import java.util.Objects;

public record NewWarmLimit(DegreeCelsius limit) implements Limit, Serializable {
    public NewWarmLimit {
        Objects.requireNonNull(limit);
    }

    public WarmLimit toWarmLimit() {
        return new WarmLimit(limit);
    }
}
