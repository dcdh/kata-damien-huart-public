package com.harvest.katadamienhuart.infrastructure;

import com.harvest.katadamienhuart.domain.*;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsUseCase;
import com.harvest.katadamienhuart.domain.usecase.AskForTemperatureUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Application {

    @Produces
    @ApplicationScoped
    public TemperatureCaptor temperatureCaptorProducer() {
        return new TemperatureCaptor() {
            private Integer currentTemperature = 10;

            @Override
            public TakenTemperature takeTemperature() {
                return new TakenTemperature(new DegreeCelsius(currentTemperature++));
            }
        };
    }

    @Produces
    @ApplicationScoped
    public TakenAtProvider sensedAtProvider() {
        return () -> {
            // I do not have any time context - choose UTC by default
            return new TakenAt(ZonedDateTime.now(ZoneOffset.UTC));
        };
    }

    @Produces
    @ApplicationScoped
    public AskForTemperatureUseCase getTemperatureUseCaseProducer(final TemperatureCaptor temperatureCaptor,
                                                                  final TakenAtProvider takenAtProvider,
                                                                  final SensorRepository sensorRepository,
                                                                  final LimitsRepository limitsRepository) {
        return new AskForTemperatureUseCase(temperatureCaptor, takenAtProvider, sensorRepository, limitsRepository);
    }

    @Produces
    @ApplicationScoped
    public RedefineLimitsUseCase redefineLimitsUseCase(final LimitsRepository limitsRepository) {
        return new RedefineLimitsUseCase(limitsRepository);
    }

}
