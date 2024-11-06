package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

public final class AskForTemperatureUseCaseTestResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext,
                                     final ExtensionContext extensionContext) throws ParameterResolutionException {
        return List.of(Temperature.class, Limits.class, TakenAt.class, Sensor.class)
                .contains(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext,
                                   final ExtensionContext extensionContext) throws ParameterResolutionException {
        if (Temperature.class.isAssignableFrom(parameterContext.getParameter().getType())) {
            return new Temperature(new DegreeCelsius(30));
        } else {
            final Limits limits = new Limits(
                    new ColdLimit(new DegreeCelsius(22)),
                    new WarmLimit(new DegreeCelsius(40))
            );
            if (Limits.class.isAssignableFrom(parameterContext.getParameter().getType())) {
                return limits;
            } else if (TakenAt.class.isAssignableFrom(parameterContext.getParameter().getType())) {
                return new TakenAt(ZonedDateTime.of(2021, 10, 1, 0, 0, 0, 0, ZoneOffset.UTC));
            } else if (Sensor.class.isAssignableFrom(parameterContext.getParameter().getType())) {
                return new Sensor(
                        new TakenTemperature(new Temperature(new DegreeCelsius(30)),
                                new TakenAt(ZonedDateTime.of(2021, 10, 1, 0, 0, 0, 0, ZoneOffset.UTC))),
                        limits
                );
            } else {
                throw new IllegalStateException("Should not be here: unsupported parameter type");
            }
        }
    }

}
