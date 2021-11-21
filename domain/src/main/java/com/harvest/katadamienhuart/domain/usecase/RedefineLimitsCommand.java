package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.ColdLimit;
import com.harvest.katadamienhuart.domain.UseCaseCommand;
import com.harvest.katadamienhuart.domain.WarnLimit;

import java.util.Objects;

public final class RedefineLimitsCommand implements UseCaseCommand {

    private final ColdLimit newColdLimit;
    private final WarnLimit newWarnLimit;

    public RedefineLimitsCommand(final ColdLimit newColdLimit,
                                 final WarnLimit newWarnLimit) {
        this.newColdLimit = Objects.requireNonNull(newColdLimit);
        this.newWarnLimit = Objects.requireNonNull(newWarnLimit);
    }

    public ColdLimit newColdLimit() {
        return newColdLimit;
    }

    public WarnLimit newWarnLimit() {
        return newWarnLimit;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof RedefineLimitsCommand)) return false;
        final RedefineLimitsCommand that = (RedefineLimitsCommand) o;
        return Objects.equals(newColdLimit, that.newColdLimit) &&
                Objects.equals(newWarnLimit, that.newWarnLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newColdLimit, newWarnLimit);
    }
}
