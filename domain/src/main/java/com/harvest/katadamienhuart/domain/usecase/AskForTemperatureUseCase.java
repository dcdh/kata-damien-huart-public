package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.*;

import java.util.Objects;

public final class AskForTemperatureUseCase implements UseCase<AskForTemperatureRequest, Sensor, AskForTemperatureException> {

    private final TemperatureCaptor temperatureCaptor;
    private final TakenAtProvider takenAtProvider;
    private final TakenTemperatureRepository takenTemperatureRepository;
    private final LimitsRepository limitsRepository;

    public AskForTemperatureUseCase(final TemperatureCaptor temperatureCaptor,
                                    final TakenAtProvider takenAtProvider,
                                    final TakenTemperatureRepository takenTemperatureRepository,
                                    final LimitsRepository limitsRepository) {
        this.temperatureCaptor = Objects.requireNonNull(temperatureCaptor);
        this.takenAtProvider = Objects.requireNonNull(takenAtProvider);
        this.takenTemperatureRepository = Objects.requireNonNull(takenTemperatureRepository);
        this.limitsRepository = Objects.requireNonNull(limitsRepository);
    }

    @Override
    public Sensor execute(final AskForTemperatureRequest request) throws AskForTemperatureException {
        Objects.requireNonNull(request);
        final Temperature temperature = temperatureCaptor.takeTemperature();
        final Limits limits = limitsRepository.findLastLimits()
                .orElseGet(Limits::ofDefault);
        final TakenAt takenAt = takenAtProvider.now();
        final TakenTemperature takenTemperature = new TakenTemperature(temperature, takenAt);
        takenTemperatureRepository.store(takenTemperature);
        return new Sensor(takenTemperature, limits);
    }

}
