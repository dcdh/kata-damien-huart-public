package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.Limits;
import com.harvest.katadamienhuart.domain.LimitsRepository;
import com.harvest.katadamienhuart.domain.UseCase;

import java.util.Objects;

public class RedefineLimitsUseCase implements UseCase<RedefineLimitsCommand, Limits> {

    private final LimitsRepository limitsRepository;

    public RedefineLimitsUseCase(final LimitsRepository limitsRepository) {
        this.limitsRepository = Objects.requireNonNull(limitsRepository);
    }

    @Override
    public Limits execute(final RedefineLimitsCommand command) {
        final Limits newLimitsDefinition = new Limits(command.newColdLimit(), command.newWarmLimit());
        limitsRepository.store(newLimitsDefinition);
        return newLimitsDefinition;
    }

}
