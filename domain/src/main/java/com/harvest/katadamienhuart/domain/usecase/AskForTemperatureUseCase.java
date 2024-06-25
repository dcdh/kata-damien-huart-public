package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.*;

import java.util.Objects;

public class AskForTemperatureUseCase implements UseCase<AskForTemperatureCommand, Sensor> {

    private final TemperatureCaptor temperatureCaptor;
    private final TakenAtProvider takenAtProvider;
    private final SensorRepository sensorRepository;
    private final LimitsRepository limitsRepository;

    public AskForTemperatureUseCase(final TemperatureCaptor temperatureCaptor,
                                    final TakenAtProvider takenAtProvider,
                                    final SensorRepository sensorRepository,
                                    final LimitsRepository limitsRepository) {
        this.temperatureCaptor = Objects.requireNonNull(temperatureCaptor);
        this.takenAtProvider = Objects.requireNonNull(takenAtProvider);
        this.sensorRepository = Objects.requireNonNull(sensorRepository);
        this.limitsRepository = Objects.requireNonNull(limitsRepository);
    }

    @Override
    public Sensor execute(final AskForTemperatureCommand command) {
        Objects.requireNonNull(command);
        final TakenTemperature takenTemperature = temperatureCaptor.takeTemperature();
        final Limits limits = limitsRepository.findLastLimits()
                .orElseGet(Limits::ofDefault);
        final TakenAt takenAt = takenAtProvider.now();
        final Sensor sensor = new Sensor(takenAt, takenTemperature, limits);
        return sensorRepository.store(sensor);
    }

}
