package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.*;

import java.util.Objects;
import java.util.Optional;

public class TakeTemperatureUseCase implements UseCase<TakeTemperatureCommand, Sensor> {

    private final TemperatureCaptor temperatureCaptor;
    private final TakenAtProvider takenAtProvider;
    private final SensorRepository sensorRepository;
    private final LimitsRepository limitsRepository;

    public TakeTemperatureUseCase(final TemperatureCaptor temperatureCaptor,
                                  final TakenAtProvider takenAtProvider,
                                  final SensorRepository sensorRepository,
                                  final LimitsRepository limitsRepository) {
        this.temperatureCaptor = Objects.requireNonNull(temperatureCaptor);
        this.takenAtProvider = Objects.requireNonNull(takenAtProvider);
        this.sensorRepository = Objects.requireNonNull(sensorRepository);
        this.limitsRepository = Objects.requireNonNull(limitsRepository);
    }

    @Override
    public Sensor execute(final TakeTemperatureCommand command) {
        final TakenTemperature takenTemperature = temperatureCaptor.takeTemperature();
        final Limits limits = Optional.ofNullable(limitsRepository.getLimits())
                .orElseGet(() -> new Limits(new ColdLimit(new DegreeCelsius(22)),
                        new WarmLimit(new DegreeCelsius(40))));
        final TakenAt takenAt = takenAtProvider.now();
        final Sensor sensor = new Sensor(takenAt, takenTemperature, limits);
        sensorRepository.save(sensor);
        return sensor;
    }

}
