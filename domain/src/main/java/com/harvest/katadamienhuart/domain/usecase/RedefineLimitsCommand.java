package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.ColdLimit;
import com.harvest.katadamienhuart.domain.UseCaseCommand;
import com.harvest.katadamienhuart.domain.WarmLimit;

import java.util.Objects;

public final class RedefineLimitsCommand implements UseCaseCommand {

    private final ColdLimit newColdLimit;
    private final WarmLimit newWarmLimit;

    public RedefineLimitsCommand(final ColdLimit newColdLimit,
                                 final WarmLimit newWarmLimit) {
        this.newColdLimit = Objects.requireNonNull(newColdLimit);
        this.newWarmLimit = Objects.requireNonNull(newWarmLimit);
    }

    public ColdLimit newColdLimit() {
        return newColdLimit;
    }

    public WarmLimit newWarmLimit() {
        return newWarmLimit;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof RedefineLimitsCommand)) return false;
        final RedefineLimitsCommand that = (RedefineLimitsCommand) o;
        return Objects.equals(newColdLimit, that.newColdLimit) &&
                Objects.equals(newWarmLimit, that.newWarmLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newColdLimit, newWarmLimit);
    }
}
