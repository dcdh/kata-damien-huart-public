package com.harvest.katadamienhuart.infrastructure;

import com.harvest.katadamienhuart.domain.*;
import com.harvest.katadamienhuart.domain.spi.LimitsRepository;
import com.harvest.katadamienhuart.domain.spi.TakenAtProvider;
import com.harvest.katadamienhuart.domain.spi.TakenTemperatureRepository;
import com.harvest.katadamienhuart.domain.spi.TemperatureCaptor;
import com.harvest.katadamienhuart.domain.usecase.AskForTemperatureUseCase;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsUseCase;
import com.harvest.katadamienhuart.domain.usecase.RetrieveLast15TakenTemperaturesUseCase;
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
            public Temperature takeTemperature() {
                return new Temperature(new DegreeCelsius(currentTemperature++));
            }
        };
    }

    @Produces
    @ApplicationScoped
    public TakenAtProvider takenAtProvider() {
        return () -> {
            // I do not have any time context - choose UTC by default
            return new TakenAt(ZonedDateTime.now(ZoneOffset.UTC));
        };
    }

    @Produces
    @ApplicationScoped
    public AskForTemperatureUseCase getTemperatureUseCaseProducer(final TemperatureCaptor temperatureCaptor,
                                                                  final TakenAtProvider takenAtProvider,
                                                                  final TakenTemperatureRepository takenTemperatureRepository,
                                                                  final LimitsRepository limitsRepository) {
        return new AskForTemperatureUseCase(temperatureCaptor, takenAtProvider, takenTemperatureRepository, limitsRepository);
    }

    @Produces
    @ApplicationScoped
    public RedefineLimitsUseCase redefineLimitsUseCase(final LimitsRepository limitsRepository) {
        return new RedefineLimitsUseCase(limitsRepository);
    }

    @Produces
    @ApplicationScoped
    public RetrieveLast15TakenTemperaturesUseCase retrieveLast15TakenTemperaturesUseCase(final LimitsRepository limitsRepository,
                                                                                         final TakenTemperatureRepository takenTemperatureRepository) {
        return new RetrieveLast15TakenTemperaturesUseCase(limitsRepository, takenTemperatureRepository);
    }

}
