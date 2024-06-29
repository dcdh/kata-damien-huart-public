package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AskForTemperatureUseCaseTest {

    @Mock
    TemperatureCaptor temperatureCaptor;

    @Mock
    TakenAtProvider takenAtProvider;

    @Mock
    TakenTemperatureRepository takenTemperatureRepository;

    @Mock
    LimitsRepository limitsRepository;

    private AskForTemperatureUseCase askForTemperatureUseCase;

    @BeforeEach
    void setup() {
        askForTemperatureUseCase = new AskForTemperatureUseCase(temperatureCaptor, takenAtProvider, takenTemperatureRepository, limitsRepository);
    }

    @Test
    void should_ask_for_temperature() throws AskForTemperatureException {
        // Given
        doReturn(TestProvider.GIVEN_TEMPERATURE).when(temperatureCaptor).takeTemperature();
        doReturn(TestProvider.GIVEN_TAKEN_AT).when(takenAtProvider).now();
        doReturn(Optional.of(TestProvider.GIVEN_LIMITS)).when(limitsRepository).findLastLimits();

        // When
        final Sensor sensor = askForTemperatureUseCase.execute(new AskForTemperatureRequest());

        // Then
        final Sensor expectedSensor = new Sensor(
                new TakenTemperature(new Temperature(new DegreeCelsius(30)),
                        new TakenAt(ZonedDateTime.of(2021, 10, 1, 0, 0, 0, 0, ZoneOffset.UTC))),
                new Limits(
                        new ColdLimit(new DegreeCelsius(22)),
                        new WarmLimit(new DegreeCelsius(40)))
        );
        assertAll(
                () -> assertThat(sensor).isEqualTo(expectedSensor),
                () -> verify(temperatureCaptor, times(1)).takeTemperature(),
                () -> verify(takenTemperatureRepository, times(1))
                        .store(new TakenTemperature(new Temperature(new DegreeCelsius(30)),
                                new TakenAt(ZonedDateTime.of(2021, 10, 1, 0, 0, 0, 0, ZoneOffset.UTC)))),
                () -> verify(takenAtProvider, times(1)).now(),
                () -> verify(limitsRepository, times(1)).findLastLimits()
        );
    }

    @Test
    void should_use_default_limits_when_not_defined() throws AskForTemperatureException {
        // Given
        doReturn(TestProvider.GIVEN_TEMPERATURE).when(temperatureCaptor).takeTemperature();
        doReturn(TestProvider.GIVEN_TAKEN_AT).when(takenAtProvider).now();
        doReturn(Optional.empty()).when(limitsRepository).findLastLimits();

        // When
        final Sensor sensor = askForTemperatureUseCase.execute(new AskForTemperatureRequest());

        // Then
        final Sensor expectedSensor = new Sensor(
                new TakenTemperature(new Temperature(new DegreeCelsius(30)),
                        new TakenAt(ZonedDateTime.of(2021, 10, 1, 0, 0, 0, 0, ZoneOffset.UTC))),
                new Limits(
                        new ColdLimit(new DegreeCelsius(22)),
                        new WarmLimit(new DegreeCelsius(40)))
        );
        assertAll(
                () -> assertThat(sensor).isEqualTo(expectedSensor),
                () -> verify(temperatureCaptor, times(1)).takeTemperature(),
                () -> verify(takenTemperatureRepository, times(1))
                        .store(new TakenTemperature(new Temperature(new DegreeCelsius(30)),
                                new TakenAt(ZonedDateTime.of(2021, 10, 1, 0, 0, 0, 0, ZoneOffset.UTC)))),
                () -> verify(takenAtProvider, times(1)).now(),
                () -> verify(limitsRepository, times(1)).findLastLimits()
        );
    }
}
