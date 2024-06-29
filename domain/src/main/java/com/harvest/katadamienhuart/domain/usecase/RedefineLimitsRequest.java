package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.ColdLimit;
import com.harvest.katadamienhuart.domain.WarmLimit;

import java.util.Objects;

public record RedefineLimitsRequest(ColdLimit newColdLimit, WarmLimit newWarmLimit) implements Request {

    public RedefineLimitsRequest {
        Objects.requireNonNull(newColdLimit);
        Objects.requireNonNull(newWarmLimit);
    }
}
