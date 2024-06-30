package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.LimitsRepository;

import java.util.Objects;

public final class RedefineLimitsUseCase implements UseCase<RedefineLimitsRequest, Limits, RedefineLimitsException> {

    private final LimitsRepository limitsRepository;

    public RedefineLimitsUseCase(final LimitsRepository limitsRepository) {
        this.limitsRepository = Objects.requireNonNull(limitsRepository);
    }

    @Override
    public Limits execute(final RedefineLimitsRequest request) throws RedefineLimitsException {
        Objects.requireNonNull(request);
        final Limits newLimitsDefinition = new Limits(request.newColdLimit(), request.newWarmLimit());
        return limitsRepository.store(newLimitsDefinition);
    }

}
