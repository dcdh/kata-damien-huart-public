package com.harvest.katadamienhuart.domain.usecase;

import com.harvest.katadamienhuart.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AskForTemperatureUseCaseTest {

    @Mock
    TemperatureCaptor temperatureCaptor;

    @Mock
    TakenAtProvider takenAtProvider;

    @Mock
    SensorRepository sensorRepository;

    @Mock
    LimitsRepository limitsRepository;

    @Mock
    AskForTemperatureUseCase askForTemperatureUseCase;

    @BeforeEach
    public void setup() {
        askForTemperatureUseCase = new AskForTemperatureUseCase(temperatureCaptor, takenAtProvider, sensorRepository, limitsRepository);
    }

    @Test
    public void should_take_temperature() {
        // Given
        doReturn(new TakenTemperature(new DegreeCelsius(30))).when(temperatureCaptor).takeTemperature();
        final ZonedDateTime sensedAt = ZonedDateTime.now();
        doReturn(new TakenAt(sensedAt)).when(takenAtProvider).now();
        doReturn(new Limits(
                new ColdLimit(new DegreeCelsius(22)),
                new WarmLimit(new DegreeCelsius(40))
        )).when(limitsRepository).getLimits();

        // When
        final Sensor sensor = askForTemperatureUseCase.execute(new AskForTemperatureCommand());

        // Then
        final Sensor expectedSensor = new Sensor(
                new TakenAt(sensedAt),
                new TakenTemperature(new DegreeCelsius(30)),
                new Limits(
                        new ColdLimit(new DegreeCelsius(22)),
                        new WarmLimit(new DegreeCelsius(40)))
        );
        assertAll(
                () -> assertThat(sensor).isEqualTo(expectedSensor),
                () -> verify(sensorRepository, times(1)).save(sensor),
                () -> verify(limitsRepository, times(1)).getLimits()
        );
    }

    @Test
    public void should_use_default_limits_when_not_defined() {
        // Given
        doReturn(new TakenTemperature(new DegreeCelsius(30))).when(temperatureCaptor).takeTemperature();
        final ZonedDateTime sensedAt = ZonedDateTime.now();
        doReturn(new TakenAt(sensedAt)).when(takenAtProvider).now();

        // When
        final Sensor sensor = askForTemperatureUseCase.execute(new AskForTemperatureCommand());

        // Then
        final Sensor expectedSensor = new Sensor(
                new TakenAt(sensedAt),
                new TakenTemperature(new DegreeCelsius(30)),
                new Limits(
                        new ColdLimit(new DegreeCelsius(22)),
                        new WarmLimit(new DegreeCelsius(40)))
        );
        assertAll(
                () -> assertThat(sensor).isEqualTo(expectedSensor),
                () -> verify(temperatureCaptor, times(1)).takeTemperature(),
                () -> verify(takenAtProvider, times(1)).now()
        );
    }
}
