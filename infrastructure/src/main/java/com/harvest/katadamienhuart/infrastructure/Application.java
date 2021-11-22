package com.harvest.katadamienhuart.infrastructure;

import com.harvest.katadamienhuart.domain.*;
import com.harvest.katadamienhuart.domain.usecase.GetTemperatureUseCase;
import com.harvest.katadamienhuart.domain.usecase.RedefineLimitsUseCase;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Application {

    @Produces
    @ApplicationScoped
    public TemperatureCaptor temperatureCaptorProducer() {
        return new TemperatureCaptor() {
            private Integer currentTemperature = 10;

            @Override
            public DegreeCelsius getTemperature() {
                return new DegreeCelsius(currentTemperature++);
            }
        };
    }

    @Produces
    @ApplicationScoped
    public SensedAtProvider sensedAtProvider() {
        return new SensedAtProvider() {
            @Override
            public SensedAt now() {
                // I do not have any time context - choose UTC by default
                return new SensedAt(ZonedDateTime.now(ZoneOffset.UTC));
            }
        };
    }

    @Produces
    @ApplicationScoped
    public GetTemperatureUseCase getTemperatureUseCaseProducer(final TemperatureCaptor temperatureCaptor,
                                                               final SensedAtProvider sensedAtProvider,
                                                               final SensorRepository sensorRepository,
                                                               final LimitsRepository limitsRepository) {
        return new GetTemperatureUseCase(temperatureCaptor, sensedAtProvider, sensorRepository, limitsRepository);
    }

    @Produces
    @ApplicationScoped
    public RedefineLimitsUseCase redefineLimitsUseCase(final LimitsRepository limitsRepository) {
        return new RedefineLimitsUseCase(limitsRepository);
    }

}
