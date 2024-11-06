package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.ColdLimit;
import com.harvest.katadamienhuart.domain.DegreeCelsius;
import com.harvest.katadamienhuart.domain.Limit;

import java.io.Serializable;
import java.util.Objects;

public record NewColdLimit(DegreeCelsius limit) implements Limit, Serializable {
    public NewColdLimit {
        Objects.requireNonNull(limit);
    }

    public ColdLimit toColdLimit() {
        return new ColdLimit(limit);
    }
}
