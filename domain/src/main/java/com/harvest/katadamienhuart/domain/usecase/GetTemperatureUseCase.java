package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.*;

import java.util.Objects;

public class GetTemperatureUseCase implements UseCase<GetTemperatureCommand, Sensor> {

    private final TemperatureCaptor temperatureCaptor;
    private final SensedAtProvider sensedAtProvider;
    private final SensorRepository sensorRepository;
    private final LimitsRepository limitsRepository;

    public GetTemperatureUseCase(final TemperatureCaptor temperatureCaptor,
                                 final SensedAtProvider sensedAtProvider,
                                 final SensorRepository sensorRepository,
                                 final LimitsRepository limitsRepository) {
        this.temperatureCaptor = Objects.requireNonNull(temperatureCaptor);
        this.sensedAtProvider = Objects.requireNonNull(sensedAtProvider);
        this.sensorRepository = Objects.requireNonNull(sensorRepository);
        this.limitsRepository = Objects.requireNonNull(limitsRepository);
    }

    @Override
    public Sensor execute(final GetTemperatureCommand command) {
        final DegreeCelsius sensedTemperature = temperatureCaptor.getTemperature();
        final Limits limits = limitsRepository.getLimits();
        final SensedAt sensedAt = sensedAtProvider.now();
        final Sensor sensor = new Sensor(sensedAt, sensedTemperature, limits);
        sensorRepository.save(sensor);
        return sensor;
    }

}
