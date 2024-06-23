package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.ColdLimit;
import com.harvest.katadamienhuart.domain.UseCaseCommand;
import com.harvest.katadamienhuart.domain.WarmLimit;

import java.util.Objects;

public record RedefineLimitsCommand(ColdLimit newColdLimit, WarmLimit newWarmLimit) implements UseCaseCommand {

    public RedefineLimitsCommand {
        Objects.requireNonNull(newColdLimit);
        Objects.requireNonNull(newWarmLimit);
    }
}
