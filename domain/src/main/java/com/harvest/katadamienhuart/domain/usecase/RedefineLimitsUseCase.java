package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.spi.LimitsRepository;

import java.util.Objects;

public final class RedefineLimitsUseCase implements UseCase<RedefineLimitsRequest, Limits, RedefineLimitsException> {

    private final LimitsRepository limitsRepository;

    public RedefineLimitsUseCase(final LimitsRepository limitsRepository) {
        this.limitsRepository = Objects.requireNonNull(limitsRepository);
    }

    @Override
    public Limits execute(final RedefineLimitsRequest request) throws RedefineLimitsException {
        try {
            Objects.requireNonNull(request);
            final Limits newLimitsDefinition = new Limits(
                    request.newColdLimit().toColdLimit(),
                    request.newWarmLimit().toWarmLimit());
            return limitsRepository.store(newLimitsDefinition);
        } catch (final Exception exception) {
            throw new RedefineLimitsException(exception);
        }
    }

}
