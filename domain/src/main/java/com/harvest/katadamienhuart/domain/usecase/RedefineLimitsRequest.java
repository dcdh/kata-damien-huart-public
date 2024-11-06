package com.harvest.katadamienhuart.domain.usecase;

import java.io.Serializable;
import java.util.Objects;

public record RedefineLimitsRequest(NewColdLimit newColdLimit,
                                    NewWarmLimit newWarmLimit) implements Request, Serializable {

    public RedefineLimitsRequest {
        Objects.requireNonNull(newColdLimit);
        Objects.requireNonNull(newWarmLimit);
    }
}
